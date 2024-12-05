import java.io.File
import kotlin.concurrent.thread
import kotlin.random.Random
var recipeList: HashSet<Recipe> = HashSet<Recipe>()
val fileName: String = "recipes.csv"
val categories: List<String> = listOf("Breakfast", "Dinner", "Side", "American", "Hispanic", "Dessert", "Asian", "French", "Snack", "Beverage", "Soup", "Salad", "Bread", "Holidays", "Appetizer", "Main-course", "Vegetarian", "Baked-goods")

fun main(args: Array<String>) {
//    This recipe finder will allow a user to enter recipe suggestions and filter them by category
menu()

}

fun menu() {
//    This function will be the main menu that will call the appropriate functions to execute the User's choices
//    Having each choice be its own function was used to allow for some choices to provide feedback to the user of
//    recipes added or database loading.
    var choice = ""
    while (choice != "5") {
        val options = "1. Load recipe database \n2. Add a recipe \n3. Filter by Category \n4. Find a Random Recipe \n5. Quit"
        println("Choose an option: ")
        println(options)
        choice = readln()
        if (choice == "1") {
            println(handleFile(fileName))
        } else if (choice == "2") {
            println(addRecipe())
        } else if (choice == "3") {
            displayFilteredRecipes()
        } else if (choice == "4") {
            val randomIndex = Random.nextInt(recipeList.size)
            println(recipeList.toList()[randomIndex].getSummary())
        } else if (choice == "5"){
            println("Have a good day!")
            break

        }
        print("\nPress enter to continue.")
        val go = readln()
        println("\n\n\n\n\n\n\n\n\n\n\n")
    }

}

fun displayFilteredRecipes() {
//    This function will ask for user input and use that input to filter down the list of recipes.
    println("Available Categories: ")
    displayCategories()
    print("Choose a Category: ")
    val index = readln().toInt() - 1
    val chosen = categories[index]
    val filteredRecipe = recipeList.filter { it.hasCategory(chosen) }
    if (filteredRecipe.isEmpty()) {
        println("No recipes available for $chosen. Please add more recipes!")
    }
    for (recipe in filteredRecipe) {
        println(recipe.getSummary())
    }
}

fun addRecipe(): String {
//    This function will get user input to create a recipe object and append it to both the recipe list and the csv file
//    returns a status update string
    print("Name: ")
    var name = readln()
    println("Available Categories:")
    displayCategories()
    var indexCat = 0
    var catList = HashSet<String>()
    while (indexCat != -1) {
        print("Choose a category (0 to escape): ")
        indexCat = readln().toInt() - 1
        if (indexCat == -1) {
            break
        }
        catList.add(categories[indexCat])
    }
    print("Total Time: ")
    var time = readln()
    println("Short Description: ")
    var description = readln()
    val newRecipe = Recipe(name, catList.toList(), time, description)
    val file = File(fileName).appendText("\n" + newRecipe.getCSVFormat())
    recipeList.add(newRecipe)

    Thread.sleep(500)
    return "$name added!"
}

fun displayCategories () {
//    Iterate through the category list to display them on the screen as selectable options
    for (category in categories) {
        var index = categories.indexOf((category)) + 1
        println("$index. $category")
    }
}

fun handleFile(fileName: String): String {
//    Call the default file and parse all the recipes into the recipe list
    File(fileName).forEachLine {
        recipeList.add(lineToRecipe(it))
    }
    return "Database loaded!"
}

fun lineToRecipe(recipeLine: String): Recipe {
//    Split the given line and parse it into a recipe object and return the object
    val recList = recipeLine.split(',')
    val categories = recList[3].split('|')
    val time = recList[1]
    return Recipe(recList[0], categories, time, recList[2])
}
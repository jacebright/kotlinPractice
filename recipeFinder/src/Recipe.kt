class Recipe(rname: String, rcategory: List<String>, rtime: String, rdescription: String) {

    private val name: String = rname
    private val totalTime: String = rtime
    private val description: String = rdescription
    private val categories: List<String> = rcategory

    fun hasCategory(category: String): Boolean {
//        Checks if a given category is applicable to the recipe
        return categories.contains(category)
    }

    fun getSummary(): String {
//        Used to provide a user-friendly summary
        return "$name: Time: $totalTime, $description"
    }

    fun getCSVFormat(): String {
//        consolidate the information into the desired format
        var recipeString = "$name,$totalTime,$description,"
        for (category in categories) {
            recipeString = "$recipeString$category|"
        }
        return recipeString
    }
}
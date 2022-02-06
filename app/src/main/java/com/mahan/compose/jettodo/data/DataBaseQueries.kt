package com.mahan.compose.jettodo.data

object DataBaseQueries {
    const val SELECT_ALL = "Select * from todo_table Order By id Asc"
    const val SELECT_TASK = "Select * from todo_table Where id=:taskId"
    const val DELETE_ALL_TASKS = "Delete from todo_table"
    const val SEARCH_DATABASE = "Select * from todo_table Where title Like :searchQuery OR description Like :searchQuery"
    const val SORT_BY_LOW_PRIORITY =
        """Select * from todo_table Order By 
                Case 
                    When priority Like 'L%'Then 1 
                    When priority Like 'M%' Then 2 
                    When priority Like 'H%' Then 3 
                End
        """
    const val SORT_BY_HIGH_PRIORITY =
        """Select * from todo_table Order By 
                Case 
                    When priority Like 'H%'Then 1 
                    When priority Like 'M%' Then 2 
                    When priority Like 'L%' Then 3 
                End
        """
}
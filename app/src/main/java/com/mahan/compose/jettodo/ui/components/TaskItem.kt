package com.mahan.compose.jettodo.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.mahan.compose.jettodo.data.models.Priority
import com.mahan.compose.jettodo.data.models.TodoTask
import com.mahan.compose.jettodo.ui.theme.*

@ExperimentalMaterialApi
@Composable
fun TaskItem(
    todoTask: TodoTask,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape,
        color = MaterialTheme.colors.taskItemBackGroundColor,
        elevation = TASK_ITEM_ELEVATION,
        onClick = {
            navigateToTaskScreen(todoTask.id)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LARGE_PADDING)
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(9f),
                    text = todoTask.title,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.taskItemTextColor,
                    maxLines = 1
                )
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    PriorityIndicator(
                        circleSize = PRIORITY_INDICATOR_SIZE,
                        priority = todoTask.priority
                    )
                }
            }
            Text(
                text = todoTask.description,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.taskItemTextColor,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TaskItemPreviewNight() {
    TaskItem(todoTask = TodoTask(priority = Priority.Low), navigateToTaskScreen = {})
}

@ExperimentalMaterialApi
@Preview()
@Composable
fun TaskItemPreview() {
    TaskItem(todoTask = TodoTask(priority = Priority.Medium), navigateToTaskScreen = {})
}
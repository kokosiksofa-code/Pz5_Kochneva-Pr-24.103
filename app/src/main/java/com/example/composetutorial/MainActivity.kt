package com.example.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import com.example.composetutorial.ui.theme.PinkTertiary
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTutorialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Conversation(SampleData.conversationSample)
                }
            }
        }
    }
}
class Message(val author: String, val body: String)

object SampleData {
    val conversationSample = listOf(
        Message("Тренер", "Привет! Готова к тренировке сегодня?"),
        Message("Тренер", "Твоя программа на сегодня:\n1. Планка - 3 подхода\n2. Приседания - 20 раз\n3. Отжимания - 15 раз"),
        Message("Система", "Твой прогресс за неделю: 5/7 тренировок. Отличный результат!"),
        Message("Тренер", "Не забудь сделать растяжку в конце.")
    )
}
@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}
@Composable
fun MessageCard(msg: Message) {
    var isExpanded by remember { mutableStateOf(false) }
    val surfaceColor by animateColorAsState(
        if (isExpanded) PinkTertiary else MaterialTheme.colorScheme.surface,
        label = "surfaceColor"
    )
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 16.dp)
            .animateContentSize()
            .clickable { isExpanded = !isExpanded },
        shape = RoundedCornerShape(24.dp),
        color = surfaceColor,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.profile_picture),
                contentDescription = "Аватар тренера",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = msg.author,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 1.dp,
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Text(
                        text = msg.body,
                        maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                        modifier = Modifier.padding(all = 4.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}
@Preview(name = "Light Mode", showBackground = true)
@Preview(
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewConversation() {
    ComposeTutorialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Conversation(SampleData.conversationSample)
        }
    }
}
package com.augieafr.todo_app.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.augieafr.todo_app.R
import com.augieafr.todo_app.ui.component.todo_appbar.TodoAppBar
import com.augieafr.todo_app.ui.navigation.Screen
import com.augieafr.todo_app.utils.noRippleClickable

@Composable
fun MyProfileScreen(
    modifier: Modifier,
    onNavigateUp: () -> Unit,
    onNavigateToBrowser: (String) -> Unit
) {
    Scaffold(modifier = modifier, topBar = {
        TodoAppBar(route = Screen.Profile.route, isShowTitle = true, onNavigationUp = onNavigateUp)
    }) {
        Column(
            modifier = modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(150.dp)
                    .border(2.dp, MaterialTheme.colorScheme.onSurface, CircleShape)
                    .shadow(10.dp, CircleShape)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.profile_pict),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = "Augie Afriyansyah",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "An Associate Android Developer who loves to learn new things, gaming, and watching movies. Let's connect!",
                style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(16.dp))
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Icon(
                    modifier = Modifier
                        .size(42.dp)
                        .noRippleClickable {
                            onNavigateToBrowser("https://www.github.com/augieafr")
                        },
                    painter = painterResource(id = R.drawable.github_mark_white),
                    contentDescription = "github"
                )
                Spacer(modifier = Modifier.size(16.dp))
                Image(
                    modifier = Modifier
                        .size(42.dp)
                        .noRippleClickable {
                            onNavigateToBrowser("https://www.linkedin.com/in/augieafr")
                        },
                    painter = painterResource(id = R.drawable.linkedin_logo),
                    contentDescription = "linkedin",
                )
                Spacer(modifier = Modifier.size(16.dp))
                Image(
                    modifier = Modifier
                        .size(42.dp)
                        .noRippleClickable {
                            onNavigateToBrowser("https://www.instagram.com/augieafr")
                        },
                    painter = painterResource(id = R.drawable.instagram_logo),
                    contentDescription = "instagram",
                )
            }
        }
    }
}
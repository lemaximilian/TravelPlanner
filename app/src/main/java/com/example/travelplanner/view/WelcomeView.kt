package com.example.travelplanner.view


import android.content.Context
import android.net.Uri
import android.provider.MediaStore.Video
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.VideoView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.travelplanner.R
import com.example.travelplanner.ui.theme.Shapes
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_ZOOM

import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.common.reflect.Reflection.getPackageName




fun getVideoUri():Uri{
val videoRes = R.raw.video
    return Uri.parse("android.resource://com.example.travelplanner/$videoRes")
}


private fun Context.buildExoPlayer(uri: Uri)= ExoPlayer.Builder(this).build().apply { setMediaItem(
    MediaItem.fromUri(uri))
repeatMode = Player.REPEAT_MODE_ALL
playWhenReady = true
prepare()
}


private fun Context.buildPlayerView(exoPlayer: ExoPlayer)= StyledPlayerView(this).apply {
    player=exoPlayer
    layoutParams = FrameLayout.LayoutParams(MATCH_PARENT,MATCH_PARENT)
    useController = false
    resizeMode = RESIZE_MODE_ZOOM

}


@Composable
fun SetName(videoUri: Uri) {
    val context = LocalContext.current
    val exoPlayer = remember {context.buildExoPlayer(videoUri)}
    
    DisposableEffect(
        AndroidView(factory ={it.buildPlayerView(exoPlayer)},
            modifier = Modifier.fillMaxSize()
        )
    ){
        onDispose {
            exoPlayer.release()
        }

    }
    



    Column(modifier = Modifier

        .padding(24.dp)
        .fillMaxSize(),

        verticalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Icon(
            painter = painterResource(id = R.drawable.logo, ),
            contentDescription = null,
            Modifier.size(250.dp),
            tint = Color.White

        )
        TextInput(Input.User)
        Button(onClick = {}, modifier = Modifier
            .padding(vertical = 15.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Blue)

        )


        {
            Text(text = "Los gehts!", Modifier.padding(vertical = 8.dp))

        }



    }
}
@Composable
fun TextInput(Input: Input){

  var value by remember {mutableStateOf("")}

      TextField(
          value = value, onValueChange = { value = it },
          Modifier
              .fillMaxWidth()
              .clip(
                  RoundedCornerShape(20.dp)
              ),
          leadingIcon = {
              Icon(
                  imageVector = Input.icon,
                  contentDescription = null

              )
          },
          label = { Text(text = Input.label) },
          shape = Shapes.small,
          colors = TextFieldDefaults.textFieldColors(
              backgroundColor = Color.White,
              focusedIndicatorColor = Color.Transparent,
              unfocusedIndicatorColor = Color.Transparent,
              disabledIndicatorColor = Color.Transparent
          ),
          singleLine = true,
          keyboardOptions = Input.keyboardOptions,
          visualTransformation = Input.visualTransformation,


          )}




sealed class Input(
    val label : String,
    val icon: ImageVector,
    val keyboardOptions: KeyboardOptions,
    val visualTransformation: VisualTransformation)

{object User : Input(label = "Username",icon = Icons.Default.Person,
    keyboardOptions= KeyboardOptions(imeAction = ImeAction.Next),
    visualTransformation = VisualTransformation.None)



 }
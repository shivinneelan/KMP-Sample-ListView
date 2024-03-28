import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun App() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()){
            if(showFlashScreen)
                FlashScreen()

            AnimatedVisibility(visible = !showFlashScreen,
                enter = fadeIn(animationSpec = tween(2000)),
                exit = fadeOut(animationSpec = tween(2000))

            ){
                IntroScreens()
            }

        }
    }
}
var showFlashScreen by mutableStateOf(true)

expect fun getPlatformName(): String
@OptIn(ExperimentalResourceApi::class)
@Composable
fun FlashScreen(){

    MaterialTheme {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {


            Image(
                painterResource("compose-multiplatform.xml"),
                null
            )

            LaunchedEffect(true) {
                delay(3000)
                showFlashScreen = false
            }

        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalResourceApi::class)
@Composable
fun IntroScreens(){
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            var showFinishButton: Boolean by remember { mutableStateOf(true) }

            val imageSlider = listOf(
                painterResource(res = "car1.jpg"),
                painterResource("car.jpg"),
                painterResource("car2.jpg")
            )
            val pagerState = rememberPagerState(pageCount = {
                imageSlider.size
            })
            Column {
                // Our page content
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    state = pagerState,
                    pageSpacing = 0.dp,
                    userScrollEnabled = true,
                    reverseLayout = false,
                    contentPadding = PaddingValues(0.dp),
                    beyondBoundsPageCount = 0,
                    pageSize = PageSize.Fill,
                    flingBehavior = PagerDefaults.flingBehavior(
                        state = pagerState
                    ),
                    key = null,
                    pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
                        orientation = Orientation.Horizontal
                    ),
                ) {
                    showFinishButton = it == imageSlider.size - 1


                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = imageSlider[it],
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(),
                            contentDescription = null
                        )

                        if (showFinishButton) {
                            Button(
                                onClick = {

                                },
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .width(120.dp),
                            ) {
                                Text(text = "Finish")
                            }
                        }
                    }

                }
            }
            LaunchedEffect(pagerState) {
                delay(1500)
                autoScroll(pagerState)
            }
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
suspend fun autoScroll(pagerState: PagerState) {
    while (true) {

        if (pagerState.currentPage < 2)
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        delay(1500)
    }
}

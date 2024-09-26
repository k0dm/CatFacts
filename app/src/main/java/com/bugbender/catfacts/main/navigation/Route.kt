package com.bugbender.catfacts.main.navigation

import android.os.Parcelable
import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.bugbender.catfacts.R
import com.bugbender.catfacts.catfacts.presentation.CatFactsScreen
import com.bugbender.catfacts.favorites.presentation.FavoritesScreen
import kotlinx.parcelize.Parcelize

@Immutable
interface Route : Parcelable {

    fun isRoot(): Boolean

    @Composable
    fun ShowContent(modifier: Modifier)

    abstract class Abstract(@StringRes val titleRes: Int, val icon: ImageVector) : Route {
        override fun isRoot() = true
    }

    @Parcelize
    object Search : Abstract(titleRes = R.string.search, icon = Icons.Default.Search) {
        @Composable
        override fun ShowContent(modifier: Modifier) {
            CatFactsScreen(modifier = modifier)
        }
    }

    @Parcelize
    object Favorites : Abstract(titleRes = R.string.favorites, icon = Icons.Default.Favorite) {
        @Composable
        override fun ShowContent(modifier: Modifier) {
            FavoritesScreen(modifier = modifier)
        }
    }

    companion object {
        val ROUTES = listOf(Search, Favorites)
    }
}

@Stable
interface Navigation {

    fun currentRoute(): Route

    fun launch(route: Route)

    fun pop()

    fun restart(route: Route)

    class Stack(private val routes: SnapshotStateList<Route>) : Navigation {

        override fun currentRoute(): Route = routes.last()

        override fun launch(route: Route) {
            routes.add(route)
        }

        override fun pop() {
            routes.removeAt(routes.lastIndex)
        }

        override fun restart(route: Route) {
            routes.apply {
                clear()
                add(route)
            }
        }
    }
}

@Composable
fun rememberNavigation(initialRoute: Route): Navigation {
    val routes = rememberSaveable(initialRoute, saver = snapshotStateListSaver()) {
        mutableStateListOf(initialRoute)
    }
    return remember {
        Navigation.Stack(
            routes = routes
        )
    }
}

private fun <T : Any> snapshotStateListSaver() = listSaver<SnapshotStateList<T>, T>(
    save = { stateList -> stateList.toList() },
    restore = { it.toMutableStateList() },
)

@Composable
fun NavigationHost(
    navigation: Navigation,
    modifier: Modifier = Modifier,
    //routeMapper: @Composable (Route) -> Unit
) {

    BackHandler(enabled = !navigation.currentRoute().isRoot()) {
        navigation.pop()
    }
    navigation.currentRoute().ShowContent(modifier)

}
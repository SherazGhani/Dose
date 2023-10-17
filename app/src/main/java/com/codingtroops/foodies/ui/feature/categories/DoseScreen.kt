package com.codingtroops.foodies.ui.feature.categories

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.codingtroops.foodies.R
import com.codingtroops.foodies.model.response.AssociatedDrug
import com.codingtroops.foodies.noRippleClickable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.Date

@ExperimentalCoilApi
@Composable
fun DoseScreen(
    remoteState: DoseContract.RemoteState,
    localState: DoseContract.LocalState,
    effectFlow: Flow<DoseContract.Effect>?,
    onNavigationRequested: () -> Unit
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    // Listen for side effects from the VM
    LaunchedEffect(effectFlow) {
        effectFlow?.onEach { effect ->
            if (effect is DoseContract.Effect.DataWasLoaded)
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Doses are loaded.",
                    duration = SnackbarDuration.Short
                )
        }?.collect()
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            DoseAppBar()
        },
    ) {
        Box {
            val sdf = SimpleDateFormat("'Date\n'dd-MM-yyyy '\n\nand\n\nTime\n'HH:mm:ss z")
            val currentDateAndTime = sdf.format(Date())
            var doseList = ArrayList<AssociatedDrug>()
            remoteState.problemResponse.problems.forEach { problem ->

                problem.diabetes.forEach {diabetes ->

                    diabetes.medications.forEach {medication ->

                        medication.medicationsClasses.forEach {medicationClass ->

                            medicationClass.className.forEach {className ->

                                className.associatedDrug.forEach {associatedDrug ->
                                    doseList.add(AssociatedDrug(
                                        name = associatedDrug.name,
                                        dose = associatedDrug.dose,
                                        strength = associatedDrug.strength,
                                        )
                                    )
                                }

                                className.associatedDrug2.forEach {associatedDrug2 ->
                                    doseList.add(AssociatedDrug(
                                        name = associatedDrug2.name,
                                        dose = associatedDrug2.dose,
                                        strength = associatedDrug2.strength,
                                        )
                                    )
                                }
                            }
                            medicationClass.className2.forEach {className2 ->

                                className2.associatedDrug.forEach {associatedDrug3 ->
                                    doseList.add(AssociatedDrug(
                                        name = associatedDrug3.name,
                                        dose = associatedDrug3.dose,
                                        strength = associatedDrug3.strength,
                                        )
                                    )
                                }

                                className2.associatedDrug2.forEach {associatedDrug22 ->
                                    doseList.add(AssociatedDrug(
                                        name = associatedDrug22.name,
                                        dose = associatedDrug22.dose,
                                        strength = associatedDrug22.strength,
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Box {
                    Text(
                        text = "Greetings\n${localState.user?.email}\n${currentDateAndTime}",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center)
                }

                DoseList(doseList = doseList) { itemId ->
                    onNavigationRequested()
                }
                if (remoteState.isLoading)
                    LoadingBar()
            }
        }
    }

}

@Composable
private fun DoseAppBar() {
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Home,
                modifier = Modifier.padding(horizontal = 12.dp),
                contentDescription = "Action icon"
            )
        },
        title = { Text(stringResource(R.string.app_name)) },
        backgroundColor = MaterialTheme.colors.background
    )
}

@Composable
fun DoseList(
    doseList: List<AssociatedDrug>,
    onItemClicked: (id: String) -> Unit = { }
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(doseList) { item ->
            DoseItemRow(item = item, itemShouldExpand = true, onItemClicked = onItemClicked)
        }
    }
}

@Composable
fun DoseItemRow(
    item: AssociatedDrug,
    itemShouldExpand: Boolean = false,
    onItemClicked: (id: String) -> Unit = { }
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .clickable { onItemClicked("") }
    ) {
        var expanded by rememberSaveable { mutableStateOf(false) }
        Column {
            Row(modifier = Modifier.animateContentSize()) {
                DoseItemDetails(
                    item = item,
                    expandedLines = if (expanded) 10 else 2,
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                            top = 24.dp,
                            bottom = 24.dp
                        )
                        .fillMaxWidth(0.80f)
                        .align(Alignment.CenterVertically)
                )
                if (itemShouldExpand)
                    Box(
                        modifier = Modifier
                            .align(if (expanded) Alignment.Bottom else Alignment.CenterVertically)
                            .noRippleClickable { expanded = !expanded }
                    ) {
                        ExpandableContentIcon(expanded)
                    }
            }
            if (expanded)
                DoseItemDetails(
                    item = item,
                    expandedLines = if (expanded) 10 else 2,
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                            top = 24.dp,
                            bottom = 24.dp
                        )
                        .fillMaxWidth(0.80f)
                        .align(Alignment.CenterHorizontally)
                )
        }
    }
}

@Composable
private fun ExpandableContentIcon(expanded: Boolean) {
    Icon(
        imageVector = if (expanded)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown,
        contentDescription = "Expand row icon",
        modifier = Modifier
            .padding(all = 16.dp)
    )
}

@Composable
fun DoseItemDetails(
    item: AssociatedDrug?,
    expandedLines: Int,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = item?.name ?: "",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle1,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            item?.strength?.let {
                Text(
                    text = it,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.caption,
                    maxLines = expandedLines
                )
            }
        }
    }
}

@Composable
fun LoadingBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}
package com.phycare.residentbeacon.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.phycare.residentbeacon.R
import com.phycare.residentbeacon.ResidentViewModel
import com.phycare.residentbeacon.getImgUrl
import com.phycare.residentbeacon.model.PGYItem
import com.phycare.residentbeacon.model.SpecialityItem
import com.phycare.residentbeacon.model.StatesItem
import com.phycare.residentbeacon.ui.theme.BeaconComposeTheme

@Composable
fun More(
    navController: NavHostController,
    viewModel: ResidentViewModel,
    stateList: SnapshotStateList<StatesItem>,
    pgyList: SnapshotStateList<PGYItem>,
    specialityList: SnapshotStateList<SpecialityItem>
) {
    BeaconComposeTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = colorResource(id = R.color.white)) {

            Box(modifier = Modifier.padding(6.dp)) {
                Column {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        viewModel.getResidentCompleteSearch("", "", "")
                        LazyRow {
                            itemsIndexed(viewModel.residentCompleteSearchListResponse) { index, item ->

                                Card(
                                    modifier = Modifier.padding(4.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = colorResource(
                                            id = R.color.white
                                        )
                                    ),
                                    elevation = CardDefaults.cardElevation(20.dp)
                                )
                                {
                                    Column {
                                        val imageUrl = getImgUrl() + item.Photo
                                        AsyncImage(
                                            model = imageUrl,
                                            contentDescription = "Resident user Image",
                                            placeholder = painterResource(id = R.drawable.doctor),
                                            error = painterResource(id = R.drawable.doctor),
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .size(80.dp)
                                                .align(Alignment.CenterHorizontally)
                                                /*.border(BorderStroke(3.dp, color = Color.Blue),
                                                 RoundedCornerShape(14.dp))*/
                                                .clip(RoundedCornerShape(14.dp)),
                                        )

                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(6.dp)
                                            //.align(Alignment.CenterVertically)
                                        )
                                        {
                                            Text(
                                                text = item.Provider_Name.trim().replace("\n", " "),
                                                style = MaterialTheme.typography.titleSmall
                                            )
                                            Text(
                                                text = "Location : " + item.Location.trim(),
                                                style = MaterialTheme.typography.titleSmall
                                            )

                                        }
                                    }

                                }
                            }
                        }
                    }
                    viewModel.getProgramCompleteSearchData("", "", "")
                    LazyVerticalGrid(columns = GridCells.Fixed(1),modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)) {
                        itemsIndexed(viewModel.programComSearchListResponse) { index, item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp), colors = CardDefaults.cardColors(
                                    containerColor = colorResource(
                                        id = R.color.white
                                    )

                                ),
                                elevation = CardDefaults.cardElevation(20.dp)
                            )
                            {
                                Column(modifier = Modifier.padding(6.dp),) {
                                    Text(
                                        text = "Program Name : " + item.programName.trim(),
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    Text(
                                        text = "Speciality : " + item.speciality.trim(),
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    Text(
                                        text = "Location : " + item.location.trim(),
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                }
                            }
                        }
                    }

                    LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2),
                        modifier = Modifier.padding(2.dp)){
                        itemsIndexed(viewModel.residentCompleteSearchListResponse) { index, item ->

                            Card(
                                modifier = Modifier.padding(4.dp, top = 6.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = colorResource(
                                        id = R.color.white
                                    )
                                ),
                                elevation = CardDefaults.cardElevation(15.dp)
                            )
                            {
                                Column {
                                    val imageUrl = getImgUrl() + item.Photo
                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = "Resident user Image",
                                        placeholder = painterResource(id = R.drawable.doctor),
                                        error = painterResource(id = R.drawable.doctor),
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(60.dp)
                                            .align(Alignment.CenterHorizontally)
                                            .border(
                                                BorderStroke(3.dp, color = colorResource(id = R.color.resident_color)),
                                                //RoundedCornerShape(14.dp)
                                                CircleShape
                                            )
                                            .clip(CircleShape),
                                    )

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(2.dp)
                                            .align(Alignment.CenterHorizontally)
                                        //.align(Alignment.CenterVertically)
                                    )
                                    {
                                        Text(
                                            text = item.Provider_Name.trim().replace("\n", " "),
                                            style = MaterialTheme.typography.titleSmall,
                                            modifier = Modifier.align(Alignment.CenterHorizontally)

                                        )
                                        Text(
                                            text = "Location : " + item.Location.trim(),
                                            style = MaterialTheme.typography.titleSmall,
                                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                        )

                                    }
                                }

                            }
                        }
                    }


                }
            }
        }
    }
}
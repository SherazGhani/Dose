package com.codingtroops.foodies.model.response

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody


data class Problem(
    @SerializedName("Diabetes")
    val diabetes: List<Diabete> = ArrayList(),
    @SerializedName("Asthma")
    val asthma: List<Unit> = ArrayList(),
)

data class Diabete(
    @SerializedName("medications")
    val medications: List<Medication> = ArrayList(),
    @SerializedName("labs")
    val labs: List<Lab> = ArrayList(),
)

data class Medication(
    @SerializedName("medicationsClasses")
    val medicationsClasses: List<MedicationsClass> = ArrayList(),
)

data class MedicationsClass(
    @SerializedName("className")
    val className: List<ClassName> = ArrayList(),
    @SerializedName("className2")
    val className2: List<ClassName2> = ArrayList(),
)

data class ClassName(
    @SerializedName("associatedDrug")
    val associatedDrug: List<AssociatedDrug> = ArrayList(),
    @SerializedName("associatedDrug#2")
    val associatedDrug2: List<AssociatedDrug2> = ArrayList(),
)

data class AssociatedDrug(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("dose")
    val dose: String = "",
    @SerializedName("strength")
    val strength: String = "",
)

data class AssociatedDrug2(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("dose")
    val dose: String = "",
    @SerializedName("strength")
    val strength: String = "",
)

data class ClassName2(
    @SerializedName("associatedDrug")
    val associatedDrug: List<AssociatedDrug3> = ArrayList(),
    @SerializedName("associatedDrug#2")
    val associatedDrug2: List<AssociatedDrug22> = ArrayList(),
)

data class AssociatedDrug3(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("dose")
    val dose: String = "",
    @SerializedName("strength")
    val strength: String = "",
)

data class AssociatedDrug22(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("dose")
    val dose: String = "",
    @SerializedName("strength")
    val strength: String = "",
)

data class Lab(
    @SerializedName("missing_field")
    val missingField: String = "",
)
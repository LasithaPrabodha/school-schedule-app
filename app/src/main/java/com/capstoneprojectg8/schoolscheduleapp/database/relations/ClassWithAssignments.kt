package com.capstoneprojectg8.schoolscheduleapp.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment
import com.capstoneprojectg8.schoolscheduleapp.models.Class

data class ClassWithAssignments(
    @Embedded val classes: Class,
    @Relation(
        parentColumn = "id",
        entityColumn = "classId"
    )
    val assignments: List<Assignment>
)

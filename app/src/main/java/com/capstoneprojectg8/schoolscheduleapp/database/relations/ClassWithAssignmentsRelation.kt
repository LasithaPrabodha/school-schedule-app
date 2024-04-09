package com.capstoneprojectg8.schoolscheduleapp.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.capstoneprojectg8.schoolscheduleapp.database.entities.AssignmentEntity
import com.capstoneprojectg8.schoolscheduleapp.database.entities.ClassEntity

data class ClassWithAssignmentsRelation(
    @Embedded val sclass: ClassEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "classId"
    )
    val assignments: List<AssignmentEntity>
)

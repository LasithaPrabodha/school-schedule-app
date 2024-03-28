package com.capstoneprojectg8.schoolscheduleapp.utils

import com.capstoneprojectg8.schoolscheduleapp.database.entities.AssignmentEntity
import com.capstoneprojectg8.schoolscheduleapp.database.entities.ClassEntity
import com.capstoneprojectg8.schoolscheduleapp.database.entities.ClassSlotEntity
import com.capstoneprojectg8.schoolscheduleapp.database.relations.ClassWithAssignments
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment
import com.capstoneprojectg8.schoolscheduleapp.models.ClassAssignments
import com.capstoneprojectg8.schoolscheduleapp.models.SClass
import com.capstoneprojectg8.schoolscheduleapp.models.ClassSlot

fun Assignment.toEntity(): AssignmentEntity {
    return AssignmentEntity(id, title, detail, isPriority, isCompleted, classId, classSlotId)
}

fun SClass.toEntity(): ClassEntity {
    return ClassEntity(id, code, name, colour)
}

fun ClassSlot.toEntity(): ClassSlotEntity {
    return ClassSlotEntity(
        id, startingHour, dayOfTheWeek, noOfHours, classId, className, classRoom, color, date, isExpandable
    )
}

fun AssignmentEntity.fromEntity(): Assignment {
    return Assignment(id, title, detail, isPriority, isCompleted, classId, classSlotId)
}

fun ClassWithAssignments.fromEntity(): ClassAssignments {
    return ClassAssignments(sclass = this.sclass.fromEntity(), assignments = this.assignments.map { it.fromEntity() })
}


fun ClassEntity.fromEntity(): SClass {
    return SClass(id, code, name, colour)
}

fun ClassSlotEntity.fromEntity(): ClassSlot {
    return ClassSlot(
        id, startingHour, dayOfTheWeek, noOfHours, classId, className, classRoom, color, date, isExpandable
    )
}
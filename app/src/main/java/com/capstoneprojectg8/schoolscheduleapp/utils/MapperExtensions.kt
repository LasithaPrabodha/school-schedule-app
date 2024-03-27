package com.capstoneprojectg8.schoolscheduleapp.utils

import com.capstoneprojectg8.schoolscheduleapp.database.entities.AssignmentEntity
import com.capstoneprojectg8.schoolscheduleapp.database.entities.ClassEntity
import com.capstoneprojectg8.schoolscheduleapp.database.entities.ClassSlotEntity
import com.capstoneprojectg8.schoolscheduleapp.models.Assignment
import com.capstoneprojectg8.schoolscheduleapp.models.Class
import com.capstoneprojectg8.schoolscheduleapp.models.ClassSlot

fun Assignment.toEntity(): AssignmentEntity {
    return AssignmentEntity(id, title, detail, isPriority, isCompleted, classId)
}

fun Class.toEntity(): ClassEntity {
    return ClassEntity(id, classCode, className, colour, isExpandable)
}

fun ClassSlot.toEntity(): ClassSlotEntity {
    return ClassSlotEntity(
        id, startingHour, dayOfTheWeek, noOfHours, className, classRoom, color, date, isExpandable
    )
}

fun AssignmentEntity.fromEntity(): Assignment {
    return Assignment(id, title, detail, isPriority, isCompleted, classId)
}

fun ClassEntity.fromEntity(): Class {
    return Class(id, classCode, className, colour, isExpandable)
}

fun ClassSlotEntity.fromEntity(): ClassSlot {
    return ClassSlot(
        id, startingHour, dayOfTheWeek, noOfHours, className, classRoom, color, date, isExpandable
    )
}
package com.accenture.mvi.presentation.model

import android.os.Parcelable
import com.accenture.mvi.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class TypeDetail(private val name: String): Parcelable {

    fun name() = name

    fun icon(): Pair<Int, Long> = when (name.uppercase()) {
        Type.BUG.name -> R.drawable.bug to 0xFFA6B91A
        Type.DARK.name -> R.drawable.dark to 0xFF595761
        Type.DRAGON.name -> R.drawable.dragon to 0xFF6F35FC
        Type.ELECTRIC.name -> R.drawable.electric to 0xFFF7D02C
        Type.FAIRY.name -> R.drawable.fairy to 0xFFEE90E6
        Type.FIGHTING.name -> R.drawable.fighting to 0xFFC22E28
        Type.FIRE.name -> R.drawable.fire to 0xFFFBA54C
        Type.FLYING.name -> R.drawable.flying to 0xFFA1BBEC
        Type.GHOST.name -> R.drawable.ghost to 0xFF735797
        Type.GRASS.name -> R.drawable.grass to 0xFF7AC74C
        Type.GROUND.name -> R.drawable.ground to 0xFFE2BF65
        Type.ICE.name -> R.drawable.ice to 0xFF96D9D6
        Type.NORMAL.name -> R.drawable.normal to 0xFFA0A29F
        Type.POISON.name -> R.drawable.poison to 0xFFA33EA1
        Type.PSYCHIC.name -> R.drawable.psychic to 0xFFFA8581
        Type.ROCK.name -> R.drawable.rock to 0xFFB6A136
        Type.STEEL.name -> R.drawable.steel to 0xFF5695A3
        Type.WATER.name -> R.drawable.water to 0xFF6390F0
        else -> throw NoSuchElementException()
    }

    private enum class Type {

        BUG,
        DARK,
        DRAGON,
        ELECTRIC,
        FIRE,
        FAIRY,
        FIGHTING,
        FLYING,
        GHOST,
        GRASS,
        GROUND,
        ICE,
        NORMAL,
        POISON,
        PSYCHIC,
        ROCK,
        STEEL,
        WATER
    }
}


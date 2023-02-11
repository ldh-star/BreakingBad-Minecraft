package org.liangguo.breakingbad.utils

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component


/**
 * @author ldh
 * 时间: 2023/1/28 19:00
 * 邮箱: ldh.liangguo@outlook.com
 */

fun String.withStyle(color: ChatFormatting) = Component.literal(this).withStyle(color)

fun String.literal() = Component.literal(this)

fun literals(vararg texts: String, color: ChatFormatting? = null) = texts.map { if (color != null) it.withStyle(color) else it.literal() }


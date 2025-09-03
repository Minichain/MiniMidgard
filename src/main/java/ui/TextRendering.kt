package ui

import Texture
import Vector3

object TextRendering {

  private val spriteSheet: Texture = Texture.loadTexture("src/main/resources/sprites/fonts/bitmap_font_white.png")
  private const val CHARACTER_WIDTH: Int = 7
  private const val CHARACTER_HEIGHT: Int = 13
  private const val COLUMNS: Int = 18
  private const val ROWS: Int = 8

  fun renderText(x: Float, y: Float, textToRender: String, scale: Float) {
    textToRender.toCharArray().forEachIndexed { i, char ->
      val charPosition = getCharacterPosition(char.toString())
      renderCharacter(charPosition[0], charPosition[1], x + (CHARACTER_WIDTH * i * scale), y, scale)
    }
  }

  private fun renderCharacter(
    characterColumn: Int,
    characterRow: Int,
    x: Float,
    y: Float,
    scale: Float,
    alpha: Float = 1f,
    r: Float = 1f,
    g: Float = 1f,
    b: Float = 1f
  ) {
    val u1 = (1f / COLUMNS.toFloat()) * characterColumn
    val v1 = (1f / ROWS.toFloat()) * characterRow
    val u2 = u1 + (1f / COLUMNS.toFloat())
    val v2 = v1 + (1f / ROWS.toFloat())
    val vertex1 =
      Vector3(
        (x + CHARACTER_WIDTH.toFloat() * scale - Window.resolution.width.toFloat() / 2f) / Window.resolution.width.toFloat(),
        (y + Window.resolution.height.toFloat() / 2f) / Window.resolution.height.toFloat(),
        1f
      )
    val vertex2 =
      Vector3(
        (x + CHARACTER_WIDTH.toFloat() * scale - Window.resolution.width.toFloat() / 2f) / Window.resolution.width.toFloat(),
        (y - CHARACTER_HEIGHT.toFloat() * scale + Window.resolution.height.toFloat() / 2f) / Window.resolution.height.toFloat(),
        1f)
    val vertex3 =
      Vector3(
        (x - Window.resolution.width.toFloat() / 2f) / Window.resolution.width.toFloat(),
        (y - CHARACTER_HEIGHT.toFloat() * scale + Window.resolution.height.toFloat() / 2f) / Window.resolution.height.toFloat(),
        1f)
    val vertex4 =
      Vector3(
        (x - Window.resolution.width.toFloat() / 2f) / Window.resolution.width.toFloat(),
        (y + Window.resolution.height.toFloat() / 2f) / Window.resolution.height.toFloat(),
        1f)
    GraphicsManager.render(spriteSheet, vertex1, vertex2, vertex3, vertex4, u1, v1, u2, v2)
  }

  private fun getCharacterPosition(character: String): IntArray {
    return when (character) {
      "Ü" -> intArrayOf(0, 0)
      "ù" -> intArrayOf(1, 0)
      "ú" -> intArrayOf(2, 0)
      "ü" -> intArrayOf(3, 0)
      "Ë" -> intArrayOf(0, 1)
      "è" -> intArrayOf(1, 1)
      "é" -> intArrayOf(2, 1)
      "ë" -> intArrayOf(3, 1)
      "Í" -> intArrayOf(4, 1)
      "Ì" -> intArrayOf(5, 1)
      "Ï" -> intArrayOf(6, 1)
      "ì" -> intArrayOf(7, 1)
      "í" -> intArrayOf(8, 1)
      "ï" -> intArrayOf(9, 1)
      "Ò" -> intArrayOf(10, 1)
      "Ó" -> intArrayOf(11, 1)
      "Ö" -> intArrayOf(12, 1)
      "ò" -> intArrayOf(13, 1)
      "ó" -> intArrayOf(14, 1)
      "ö" -> intArrayOf(15, 1)
      "Ù" -> intArrayOf(16, 1)
      "Ú" -> intArrayOf(17, 1)
      "z" -> intArrayOf(0, 2)
      "{" -> intArrayOf(1, 2)
      "|" -> intArrayOf(2, 2)
      "}" -> intArrayOf(3, 2)
      "~" -> intArrayOf(4, 2)
      "·" -> intArrayOf(5, 2)
      "Ñ" -> intArrayOf(6, 2)
      "ñ" -> intArrayOf(7, 2)
      "Ç" -> intArrayOf(8, 2)
      "ç" -> intArrayOf(9, 2)
      "À" -> intArrayOf(10, 2)
      "Á" -> intArrayOf(11, 2)
      "Ä" -> intArrayOf(12, 2)
      "à" -> intArrayOf(13, 2)
      "á" -> intArrayOf(14, 2)
      "ä" -> intArrayOf(15, 2)
      "È" -> intArrayOf(16, 2)
      "É" -> intArrayOf(17, 2)
      "h" -> intArrayOf(0, 3)
      "i" -> intArrayOf(1, 3)
      "j" -> intArrayOf(2, 3)
      "k" -> intArrayOf(3, 3)
      "l" -> intArrayOf(4, 3)
      "m" -> intArrayOf(5, 3)
      "n" -> intArrayOf(6, 3)
      "o" -> intArrayOf(7, 3)
      "p" -> intArrayOf(8, 3)
      "q" -> intArrayOf(9, 3)
      "r" -> intArrayOf(10, 3)
      "s" -> intArrayOf(11, 3)
      "t" -> intArrayOf(12, 3)
      "u" -> intArrayOf(13, 3)
      "v" -> intArrayOf(14, 3)
      "w" -> intArrayOf(15, 3)
      "x" -> intArrayOf(16, 3)
      "y" -> intArrayOf(17, 3)
      "V" -> intArrayOf(0, 4)
      "W" -> intArrayOf(1, 4)
      "X" -> intArrayOf(2, 4)
      "Y" -> intArrayOf(3, 4)
      "Z" -> intArrayOf(4, 4)
      "[" -> intArrayOf(5, 4)
      "\\" -> intArrayOf(6, 4)
      "]" -> intArrayOf(7, 4)
      "^" -> intArrayOf(8, 4)
      "_" -> intArrayOf(9, 4)
      "´" -> intArrayOf(10, 4)
      "a" -> intArrayOf(11, 4)
      "b" -> intArrayOf(12, 4)
      "c" -> intArrayOf(13, 4)
      "d" -> intArrayOf(14, 4)
      "e" -> intArrayOf(15, 4)
      "f" -> intArrayOf(16, 4)
      "g" -> intArrayOf(17, 4)
      "D" -> intArrayOf(0, 5)
      "E" -> intArrayOf(1, 5)
      "F" -> intArrayOf(2, 5)
      "G" -> intArrayOf(3, 5)
      "H" -> intArrayOf(4, 5)
      "I" -> intArrayOf(5, 5)
      "J" -> intArrayOf(6, 5)
      "K" -> intArrayOf(7, 5)
      "L" -> intArrayOf(8, 5)
      "M" -> intArrayOf(9, 5)
      "N" -> intArrayOf(10, 5)
      "O" -> intArrayOf(11, 5)
      "P" -> intArrayOf(12, 5)
      "Q" -> intArrayOf(13, 5)
      "R" -> intArrayOf(14, 5)
      "S" -> intArrayOf(15, 5)
      "T" -> intArrayOf(16, 5)
      "U" -> intArrayOf(17, 5)
      "2" -> intArrayOf(0, 6)
      "3" -> intArrayOf(1, 6)
      "4" -> intArrayOf(2, 6)
      "5" -> intArrayOf(3, 6)
      "6" -> intArrayOf(4, 6)
      "7" -> intArrayOf(5, 6)
      "8" -> intArrayOf(6, 6)
      "9" -> intArrayOf(7, 6)
      ":" -> intArrayOf(8, 6)
      ";" -> intArrayOf(9, 6)
      "<" -> intArrayOf(10, 6)
      "=" -> intArrayOf(11, 6)
      ">" -> intArrayOf(12, 6)
      "?" -> intArrayOf(13, 6)
      "@" -> intArrayOf(14, 6)
      "A" -> intArrayOf(15, 6)
      "B" -> intArrayOf(16, 6)
      "C" -> intArrayOf(17, 6)
      " " -> intArrayOf(0, 7)
      "!" -> intArrayOf(1, 7)
      "\"" -> intArrayOf(2, 7)
      "#" -> intArrayOf(3, 7)
      "$" -> intArrayOf(4, 7)
      "%" -> intArrayOf(5, 7)
      "&" -> intArrayOf(6, 7)
      "'" -> intArrayOf(7, 7)
      "(" -> intArrayOf(8, 7)
      ")" -> intArrayOf(9, 7)
      "*" -> intArrayOf(10, 7)
      "+" -> intArrayOf(11, 7)
      "," -> intArrayOf(12, 7)
      "-" -> intArrayOf(13, 7)
      "." -> intArrayOf(14, 7)
      "/" -> intArrayOf(15, 7)
      "0" -> intArrayOf(16, 7)
      "1" -> intArrayOf(17, 7)
      else -> intArrayOf(17, 7)
    }
  }
}
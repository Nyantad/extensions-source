package eu.kanade.tachiyomi.extension.fr.astralmanga

import eu.kanade.tachiyomi.source.model.SManga
import kotlinx.serialization.Serializable

@Serializable
class PresignResponseDto(
    val url: String,
)

@Serializable
class MangaResponseDto(
    val mangas: List<MangaDto>,
    val total: Int,
)

@Serializable
class MangaDto(
    val id: String,
    val title: String,
    val description: String? = null,
    val urlId: String,
    val cover: CoverDto? = null,
    val status: String? = null,
    val type: String? = null,
) {
    fun toSManga(presignS3Key: (String) -> String?): SManga = SManga.create().apply {
        url = "/manga/$urlId"
        title = this@MangaDto.title
        thumbnail_url = cover?.image?.link?.let { link ->
            if (link.startsWith("s3:")) {
                presignS3Key(link.substringAfter("s3:"))
            } else {
                link
            }
        }
    }
}

@Serializable
class CoverDto(
    val image: ImageDto? = null,
)

@Serializable
class ImageDto(
    val link: String,
)

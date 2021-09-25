package com.example.firstapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firstapp.databinding.ActivityMainBinding

fun getFormattedNumber(number: Int): String {
    val numberChars = number.toString().toCharArray().toCollection(ArrayList())
    val result = when (number) {
        in 1000..9999 -> "${numberChars[0]}.${numberChars[1]}K"
        in 10000..99999 -> "${numberChars[0]}${numberChars[1]}K"
        in 100000..999999 -> "${numberChars[0]}${numberChars[1]}${numberChars[2]}K"
        in 1000000..1099999 -> "${numberChars[0]}M"
        in 1100000..9999999 -> "${numberChars[0]}.${numberChars[1]}M"
        in 10000000..99999999 -> "${numberChars[0]}${numberChars[1]}.${numberChars[2]}M"
        else -> number.toString()
    }
    return result
}

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработе, аналитике и управвлнию. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен - http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likes = 1499,
            shares = 15
        )
        with(binding) {
            authorName.text = post.author
            content.text = post.content
            published.text = post.published
            likesCount.text = getFormattedNumber(post.likes)
            sharesCount.text = getFormattedNumber(post.shares)
            viewsCount.text = post.views.toString()
            if (post.likedByMe) {
                likes.setImageResource(R.drawable.ic_liked)
            }
            if (post.sharedByMe) {
                shares.setImageResource(R.drawable.ic_shared)
            }

            likes.setOnClickListener {
                post.likedByMe = !post.likedByMe
                likes.setImageResource(
                    if (post.likedByMe) {
                        post.likes++
                        likesCount.text = getFormattedNumber(post.likes)
                        R.drawable.ic_liked
                    } else {
                        post.likes--
                        likesCount.text = getFormattedNumber(post.likes)
                        R.drawable.ic_baseline_favorite_border_24
                    }
                )
            }

            shares.setOnClickListener {
                post.sharedByMe = !post.sharedByMe
                shares.setImageResource(
                    if (post.sharedByMe) {
                        post.shares++
                        sharesCount.text = getFormattedNumber(post.shares)
                        R.drawable.ic_shared
                    } else {
                        post.shares--
                        sharesCount.text = getFormattedNumber(post.shares)
                        R.drawable.ic_baseline_share_24
                    }
                )
            }


        }
    }
}
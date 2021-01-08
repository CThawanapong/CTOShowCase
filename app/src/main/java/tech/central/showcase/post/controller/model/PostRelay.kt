package tech.central.showcase.post.controller.model

import android.widget.TextView
import tech.central.showcase.base.model.Post

data class PostRelay(val post: Post,
                     val tvTitle: TextView,
                     val tvName: TextView,
                     val tvEmail: TextView)
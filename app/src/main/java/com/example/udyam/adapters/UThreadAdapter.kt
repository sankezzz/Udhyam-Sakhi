import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.udyam.databinding.ItemPostsBinding


class UThreadAdapter(
    private val threadList: List<UThread>
) : RecyclerView.Adapter<UThreadAdapter.UThreadViewHolder>() {

    inner class UThreadViewHolder(val binding: ItemPostsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UThreadViewHolder {
        val binding = ItemPostsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UThreadViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UThreadViewHolder, position: Int) {
        val uThread = threadList[position]
        val b = holder.binding

        // Use itemView context for Glide to avoid UnsupportedOperationException
        Glide.with(holder.itemView)
            .load(uThread.userImageUrl)
            .into(b.dp)

        b.namePerson.text = uThread.userName
//        b.time.text = uThread.timestamp
        b.textTitle.text = uThread.title
        b.description.text = uThread.description
        b.viewsCount.text = uThread.viewCount.toString()
        b.commentsCount.text = uThread.commentCount.toString()

        Glide.with(holder.itemView)
            .load(uThread.imageUrl)
            .into(b.threadImg)

        // Click listeners (optional)
        b.like.setOnClickListener {
            // Handle like
        }

        b.sharing.setOnClickListener {
            // Handle share
        }

        b.commenting.setOnClickListener {
            // Handle comment
        }
    }

    override fun getItemCount(): Int = threadList.size
}

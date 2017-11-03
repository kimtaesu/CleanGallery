package com.hucet.clean.gallery.gallery.adapter

import android.support.v7.util.DiffUtil
import com.hucet.clean.gallery.model.Medium


/**
 * Created by taesu on 2017-10-31.
 */
/**
 * areItemsTheSame(int oldItemPosition, int newItemPosition):
 * 두 객체가 같은 항목인지 여부를 결정합니다.
 *
 * areContentsTheSame(int oldItemPosition, int newItemPosition):
 * 두 항목의 데이터가 같은지 여부를 결정합니다. areItemsTheSame()이 true를 반환하는 경우에만 호출됩니다.
 *
 * getChangePayload(int oldItemPosition, int newItemPosition):
 * areItemTheSame()이 true를 반환하고 areContentsTheSame()이 false를 반환하면 이 메서드가 호출되어 변경 내용에 대한 페이로드를 가져옵니다.
 */
class MediumDiffCallback(private val oldList: List<Medium>, private val newList: List<Medium>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    //    두 객체가 같은 항목인지 여부를 결정합니다.
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].path == newList[newItemPosition].path
    }

    //    두 항목의 데이터가 같은지 여부를 결정합니다. areItemsTheSame()이 true를 반환하는 경우에만 호출됩니다.
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem === newItem
    }
}
package com.haoyu.app.adapter;

import android.content.Context;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoyu.app.basehelper.BaseArrayRecyclerAdapter;
import com.haoyu.app.entity.CourseChildSectionEntity;
import com.haoyu.app.entity.CourseSectionEntity;
import com.haoyu.app.entity.MultiItemEntity;
import com.haoyu.app.lingnan.teacher.R;
import com.haoyu.app.utils.PixelFormat;

import java.util.List;

/**
 * 创建日期：2017/9/12 on 16:36
 * 描述:
 * 作者:马飞奔 Administrator
 */
public class CourseSectionAdapter extends BaseArrayRecyclerAdapter<MultiItemEntity> {

    private Context context;
    private OnSectionClickListener onSectionClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public CourseSectionAdapter(Context context, List<MultiItemEntity> mDatas) {
        super(mDatas);
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getItemType();
    }

    @Override
    public int bindView(int viewtype) {
        if (viewtype == 0)
            return R.layout.course_section_item;
        else
            return R.layout.course_section_child_item;
    }

    @Override
    public void onBindHoder(RecyclerHolder holder, MultiItemEntity item, final int position) {
        int viewType = holder.getItemViewType();
        if (viewType == 0) {
            final CourseSectionEntity sectionEntity = (CourseSectionEntity) item;
            final TextView tv_title = holder.obtainView(R.id.section_title);
            setSpannedText(sectionEntity.getTitle(), tv_title);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onItemLongClickListener != null)
                        onItemLongClickListener.onItemLongClick(tv_title, sectionEntity.getTitle());
                    return false;
                }
            });
        } else {
            final CourseChildSectionEntity childEntity = (CourseChildSectionEntity) item;
            LinearLayout ll_layout = holder.obtainView(R.id.ll_layout);
            final TextView tv_title = holder.obtainView(R.id.tv_selection_title);
            ImageView ic_selection_state = holder.obtainView(R.id.ic_selection_state);
            ic_selection_state.setVisibility(View.GONE);
            setSpannedText(childEntity.getTitle(), tv_title, ll_layout);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onSectionClickListener != null)
                        onSectionClickListener.onSectionSelected(childEntity);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onItemLongClickListener != null)
                        onItemLongClickListener.onItemLongClick(tv_title, childEntity.getTitle());
                    return false;
                }
            });
        }
    }

    private void setSpannedText(String title, TextView tv) {
        int left, top, right, bottom;
        if (title == null || title.trim().length() == 0) {
            left = right = PixelFormat.dp2px(context, 12);
            top = bottom = PixelFormat.dp2px(context, 6);
            tv.setPadding(left, top, right, bottom);
            tv.setText("无标题");
        } else {
            Spanned spanned = Html.fromHtml(title);
            SpannableString ss = new SpannableString(spanned);
            if (title.contains("<sup>")) {
                ss.setSpan(new SuperscriptSpan(), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                left = right = PixelFormat.dp2px(context, 12);
                top = bottom = PixelFormat.dp2px(context, 2);
                tv.setPadding(left, top, right, bottom);
                tv.setText(ss);
            } else if (title.contains("<sub>")) {
                ss.setSpan(new SubscriptSpan(), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                left = right = PixelFormat.dp2px(context, 12);
                top = bottom = PixelFormat.dp2px(context, 2);
                tv.setPadding(left, top, right, bottom);
                tv.setText(ss);
            } else {
                left = right = PixelFormat.dp2px(context, 12);
                top = bottom = PixelFormat.dp2px(context, 6);
                tv.setPadding(left, top, right, bottom);
                tv.setText(spanned);
            }
        }
    }

    private void setSpannedText(String title, TextView tv_title, LinearLayout layout) {
        int left, top, right, bottom;
        if (title == null || title.trim().length() == 0) {
            tv_title.setText("无标题");
            left = right = top = bottom = PixelFormat.dp2px(context, 12);
            layout.setPadding(left, top, right, bottom);
        } else {
            Spanned spanned = Html.fromHtml(title);
            SpannableString ss = new SpannableString(spanned);
            if (title.contains("<sup>")) {
                ss.setSpan(new SuperscriptSpan(), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                left = right = PixelFormat.dp2px(context, 12);
                top = bottom = PixelFormat.dp2px(context, 8);
                layout.setPadding(left, top, right, bottom);
                tv_title.setText(ss);
            } else if (title.contains("<sub>")) {
                ss.setSpan(new SubscriptSpan(), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                left = right = PixelFormat.dp2px(context, 12);
                top = bottom = PixelFormat.dp2px(context, 8);
                layout.setPadding(left, top, right, bottom);
                tv_title.setText(ss);
            } else {
                left = right = top = bottom = PixelFormat.dp2px(context, 12);
                layout.setPadding(left, top, right, bottom);
                tv_title.setText(spanned);
            }
        }
    }

    public interface OnSectionClickListener {
        void onSectionSelected(CourseChildSectionEntity entity);
    }

    public void setOnSectionClickListener(OnSectionClickListener onSectionClickListener) {
        this.onSectionClickListener = onSectionClickListener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(TextView tv, CharSequence charSequence);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
}

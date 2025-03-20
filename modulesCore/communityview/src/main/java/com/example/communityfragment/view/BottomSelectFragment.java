package com.example.communityfragment.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.communityfragment.R;
import com.example.communityfragment.databinding.FragmentBottomSelectBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSelectFragment extends BottomSheetDialogFragment {
    private FragmentBottomSelectBinding binding;
    private int communityId;
    private OnOptionSelectedListener listener;

    public interface OnOptionSelectedListener {
        void onOptionSelected(int selectedOption);
    }

    public void setOnOptionSelectedListener(OnOptionSelectedListener listener) {
        this.listener = listener;
    }

    public BottomSelectFragment(int communityId) {
        this.communityId = communityId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBottomSelectBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setSelectedOption(communityId);

        // 设置点击监听
        binding.optionChat.setOnClickListener(v -> handleSelection(0));
        binding.optionInfo.setOnClickListener(v -> handleSelection(1));
        binding.optionPlant.setOnClickListener(v -> handleSelection(2));
    }

    private void handleSelection(int position) {
        setSelectedOption(position);
        if (listener != null) {
            listener.onOptionSelected(position);
            Log.d("BottomSelectFragmentTAG", "Selected option: " + position);
        }
        dismiss();
    }

    private void setSelectedOption(int selectedPosition) {
        // 重置所有图标
        binding.ivChatSelected.setImageResource(R.drawable.ic_notcheck);
        binding.ivInfoSelected.setImageResource(R.drawable.ic_notcheck);
        binding.ivPlantSelected.setImageResource(R.drawable.ic_notcheck);

        // 设置选中图标
        switch (selectedPosition) {
            case 0:
                binding.ivChatSelected.setImageResource(R.drawable.ic_check);
                break;
            case 1:
                binding.ivPlantSelected.setImageResource(R.drawable.ic_check);
                break;
            case 2:
                binding.ivInfoSelected.setImageResource(R.drawable.ic_check);
                break;
        }
    }
}
package com.example.comp4200project;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class LoginPagerAdapter extends FragmentStateAdapter {

    public LoginPagerAdapter(@NonNull FragmentActivity fragmentActivity)
    {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position)
    {
        switch(position)
        {
            case 0:
                return new AccountLoginFragment();
            case 1:
                return new CardLoginFragment();
            default:
                throw new IllegalArgumentException("[LOGIN PAGE] Invalid position: " + position);
        }
    }

    @Override
    public int getItemCount()
    {
        return 2;
    }
}

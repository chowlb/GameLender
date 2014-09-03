package com.chowlb.gamelender.adapters;

import java.util.Locale;

import com.chowlb.gamelender.R;
import com.chowlb.gamelender.ui.InboxFragment;
import com.chowlb.gamelender.ui.FriendsFragment;
import com.chowlb.gamelender.ui.LibraryFragment;
import com.chowlb.gamelender.ui.BorrowedFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

	protected Context mContext;
	
	public SectionsPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		this.mContext = context;
	}

	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given page.
		// Return a DummySectionFragment (defined as a static inner class
		// below) with the page number as its lone argument.
		switch(position) {
			case 2:		
				return new FriendsFragment();
			case 0:   
				return new LibraryFragment();
			case 3:
				return new InboxFragment();
			case 1:
				return new BorrowedFragment();
		}
		
		return null;
	}

	@Override
	public int getCount() {
		// Show 3 total pages.
		return 4;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 2:
			return mContext.getString(R.string.title_section1).toUpperCase(l);
		case 0:
			return mContext.getString(R.string.title_section2).toUpperCase(l);
		case 3:
			return mContext.getString(R.string.title_section3).toUpperCase(l);
		case 1:
			return mContext.getString(R.string.title_section4).toUpperCase(l);
		}
			
		return null;
	}
	
	public int getPageIcon(int position) {
		
		switch (position) {
		case 2:
			return R.drawable.ic_tab_friends;
		case 0:
			return R.drawable.ic_tab_library;
		case 3:
			return R.drawable.ic_tab_inbox;
		case 1:
			return R.drawable.ic_tab_borrowed;
		}
		return R.drawable.ic_tab_library;
	}
}

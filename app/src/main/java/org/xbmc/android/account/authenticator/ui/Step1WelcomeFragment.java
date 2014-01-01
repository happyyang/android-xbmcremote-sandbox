/*
 *      Copyright (C) 2005-2015 Team XBMC
 *      http://xbmc.org
 *
 *  This Program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2, or (at your option)
 *  any later version.
 *
 *  This Program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with XBMC Remote; see the file license.  If not, write to
 *  the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.
 *  http://www.gnu.org/copyleft/gpl.html
 *
 */

package org.xbmc.android.account.authenticator.ui;

import android.os.Bundle;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.view.FragmentStateManager;
import org.xbmc.android.view.RelativePagerFragment;

public class Step1WelcomeFragment extends WizardFragment {

	public Step1WelcomeFragment() {
		super(R.layout.fragment_auth_wizard_01_welcome);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fragmentStateManager.initFragment(savedInstanceState, Step2aSearchingFragment.class);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		fragmentStateManager.putFragment(outState, Step2aSearchingFragment.class);
	}

	@Override
	int hasNextButton() {
		return STATUS_ENABLED;
	}

	@Override
	int hasPrevButton() {
		return STATUS_GONE;
	}

	@Override
	int getStep() {
		return 0;
	}

	@Override
	public RelativePagerFragment getNext(FragmentStateManager fsm) {
		return fsm.getFragment(Step2aSearchingFragment.class);
	}
}

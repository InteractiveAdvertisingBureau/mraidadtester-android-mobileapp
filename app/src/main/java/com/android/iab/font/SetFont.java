/*******************************************************************************
 Copyright (c) 2015, Interactive Advertising Bureau
 All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 ******************************************************************************/

package com.android.iab.font;

import java.lang.reflect.Field;

import android.app.Activity;
import android.graphics.Typeface;

public class SetFont {

	Activity mActivity;

public SetFont(Activity mActivity) {
		// TODO Auto-generated constructor stub
	this.mActivity=mActivity;
	setDefaultTypeFace();
	setMonoSpaceTypeFace();
	setSansTypeFace();
	}

	public void  setDefaultTypeFace(){
		try {
			Typeface defaultTypeFace = Typeface.createFromAsset(mActivity.getAssets(),
                    "fonts/Futura-Std-Book.ttf");
			final Field StaticField1 = Typeface.class
                    .getDeclaredField("DEFAULT");
			StaticField1.setAccessible(true);
			StaticField1.set(null, defaultTypeFace);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void setSansTypeFace() {
		try {
			Typeface normalTypeFace = Typeface.createFromAsset(mActivity.getAssets(),
					"fonts/Futura-Std-Heavy.ttf");
			final Field StaticField2 = Typeface.class
					.getDeclaredField("SANS_SERIF");
			StaticField2.setAccessible(true);
			StaticField2.set(null, normalTypeFace);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void  setMonoSpaceTypeFace() {
		try {
			Typeface monospaceTypeFace = Typeface.createFromAsset(mActivity.getAssets(),
					"fonts/futura_std_bold.ttf");
			final Field StaticField3 = Typeface.class
					.getDeclaredField("MONOSPACE");
			StaticField3.setAccessible(true);
			StaticField3.set(null, monospaceTypeFace);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	}

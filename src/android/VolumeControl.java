/*
 * Phonegap VolumeControl Plugin for Android
 * Cordova 2.2.0
 * Author: Manuel Simpson
 * Email: manusimpson[at]gmail[dot]com
 * Date: 12/28/2012
 */

package com.develcode.plugins.volumeControl;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.LOG;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.media.*;

public class VolumeControl extends CordovaPlugin {

  public static final String SET = "setVolume";
  public static final String GET = "getVolume";
  public static final String GET_MAX = "getMaxVolume";
  private static final String TAG = "VolumeControl";

  private Context context;
  private AudioManager manager;

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    boolean actionState = true;
    context = cordova.getActivity().getApplicationContext();
    manager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

    if (SET.equals(action)) {
      int volume = args.getInt(0);
      boolean play_sound;
      try {
        play_sound = args.getBoolean(1);
      } catch(JSONException e) {
        play_sound = true;
      }

      setStreamVolume(volume, play_sound);
      callbackContext.success();
    } else if (GET.equals(action)) {
      callbackContext.success(getStreamVolume());
    } else if (GET_MAX.equals(action)) {
      callbackContext.success(getStreamMaxVolume());
    } else {
      actionState = false;
    }

    return actionState;
  }

  private void setStreamVolume(int volume, boolean play_sound) {
    manager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, (play_sound ? AudioManager.FLAG_PLAY_SOUND : 0));
  }

  private int getMaxVolume() {
    return manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
  }

  private int getCurrentVolume() {
    return manager.getStreamVolume(AudioManager.STREAM_MUSIC);
  }
}

package com.luzi82.android.wallpaper.gol;


import java.util.Random;

import android.graphics.Canvas;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class LiveWallpaper extends WallpaperService {
  
  private static final String LOG_TAG = "LiveWallpaper";
  
  @Override
  public void onCreate() {
    super.onCreate();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }
  
  @Override
  public Engine onCreateEngine() {
    // Create the live wallpaper engine
    return new LiveWallpaperEngine();
  }

  class LiveWallpaperEngine extends Engine {
    
    @Override
    public void onCreate(SurfaceHolder holder) {
      super.onCreate(holder);
      // Enable touch
      setTouchEventsEnabled(true);
    }

    @Override
    public void onDestroy() {
      super.onDestroy();
    }

    // Become false when switching to an app or put phone to sleep
    @Override
    public void onVisibilityChanged(boolean visible) {
      super.onVisibilityChanged(visible);
      Log.d(LOG_TAG, "onVisibilityChanged="  + visible);
    }

    // 0 when on the first home screen, -0.5/-160px on the center
    // home screen (assume 3 screens in total).
    @Override
    public void onOffsetsChanged(float xOffset, float yOffset,
        float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
      super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep,
          xPixelOffset, yPixelOffset);
      Log.d(LOG_TAG, "xOffset=" + xOffset + " yOffset=" + yOffset +
          "xOffseStep=" + xOffsetStep + " yOffsetStep=" + yOffsetStep +
          "xPixelOffset=" + xPixelOffset + " yPixelOffset=" + yPixelOffset);
    }

    @Override
    public void onSurfaceChanged(SurfaceHolder holder, int format, int width,
        int height) {
      super.onSurfaceChanged(holder, format, width, height);
      Log.d(LOG_TAG, "onSurfaceChanged");
    }

    @Override
    public void onSurfaceCreated(SurfaceHolder holder) {
      super.onSurfaceCreated(holder);
      Log.d(LOG_TAG, "onSurfaceCreated");
    }

    @Override
    public void onSurfaceDestroyed(SurfaceHolder holder) {
      super.onSurfaceDestroyed(holder);
      Log.d(LOG_TAG, "onSurfaceDestroyed");
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
      super.onTouchEvent(event);
      // Change the wallpaper color
      if (event.getAction() == MotionEvent.ACTION_UP) {
        Log.d(LOG_TAG, "touch!");
        Random rand = new Random(System.currentTimeMillis());
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);
        updateWallpaperColor(r, g, b);
      }
    }
    
    private void updateWallpaperColor(int r, int g, int b) {
      // Get the SurfaceHolder
      SurfaceHolder holder = getSurfaceHolder();
      Canvas c = null;
      try {
        c = holder.lockCanvas();
        if (c != null) {
          c.drawRGB(r, g, b);
        }
      } finally {
        if (c != null)
          holder.unlockCanvasAndPost(c);
      }
    }
    
  }

}

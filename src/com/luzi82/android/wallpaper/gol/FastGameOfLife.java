package com.luzi82.android.wallpaper.gol;

public class FastGameOfLife {

	final int width;
	final int height;
	final int size;
	final int[] map; // on: 2, off: 0
	final int[] calc_buffer;

	public FastGameOfLife(int width, int height) {
		this.width = width;
		this.height = height;
		this.size = width * height;
		this.map = new int[this.size];
		this.calc_buffer = new int[this.size];
	}

	public void step() {
		final int w = this.width;
		final int h = this.height;
		final int size = this.size;
		final int[] map = this.map;
		final int[] calc_buffer = this.calc_buffer;

		final int w1 = w - 1;
		final int h1 = h - 1;

		int x, y;
		int i;
		int start;

		// self copy
		for (i = 0; i < size; ++i) {
			calc_buffer[i] = map[i] >> 1;
		}

		// add horizontal
		start = 0;
		for (y = 0; y < h; ++y) {
			// inner
			i = start;
			for (x = 0; x < w1; ++x) {
				calc_buffer[i] += map[i + 1];
				calc_buffer[i + 1] += map[i];
				++i;
			}
			start += w;
			// edge
			calc_buffer[start] += map[start + w1];
			calc_buffer[start + w1] += map[start];
		}

		// add vertical
		// inner
		start = 0;
		for (y = 0; y < h1; ++y) {
			i = start;
			for (x = 0; x < w; ++x) {
				calc_buffer[i] += map[i + w];
				calc_buffer[i + w] += map[i];
				++i;
			}
			start += w;
		}
		// edge
		i = w * h1;
		for (x = 0; x < w; ++x) {
			calc_buffer[x] += map[i];
			calc_buffer[i] += map[x];
			++i;
		}

		// counting
		for (i = 0; i < size; ++i) {
			if ((calc_buffer[i] > 4) && (calc_buffer[i] < 8)) {
				map[i] = 2;
			}
		}
	}

}

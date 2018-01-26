package cc.chinagps.gboss.comcenter.buff;

public class BigHash {
	public static BigHash bigHash = new BigHash();

	private int rot(int x, int k) {
		return (x << k) ^ (x >>> (32 - k));
	}

	private void mix(Int ia, Int ib, Int ic) {
		int a = ia.getValue();
		int b = ib.getValue();
		int c = ic.getValue();

		a -= c;
		a ^= rot(c, 4);
		c += b;
		b -= a;
		b ^= rot(a, 6);
		a += c;
		c -= b;
		c ^= rot(b, 8);
		b += a;
		a -= c;
		a ^= rot(c, 16);
		c += b;
		b -= a;
		b ^= rot(a, 19);
		a += c;
		c -= b;
		c ^= rot(b, 4);
		b += a;

		ia.setValue(a);
		ib.setValue(b);
		ic.setValue(c);
	}

	private void finalx(Int ia, Int ib, Int ic) {
		int a = ia.getValue();
		int b = ib.getValue();
		int c = ic.getValue();

		c ^= b;
		c -= rot(b, 14);

		a ^= c;
		a -= rot(c, 11);
		b ^= a;
		b -= rot(a, 25);
		c ^= b;
		c -= rot(b, 16);
		a ^= c;
		a -= rot(c, 4);
		b ^= a;
		b -= rot(a, 14);
		c ^= b;
		c -= rot(b, 24);

		ia.setValue(a);
		ib.setValue(b);
		ic.setValue(c);
	}

	private int getInt(byte[] buff, int start) {
		int position = start;
		int v = 0;
		for (int i = 0; i < 4; i++) {
			int ax = buff[position] & 0xff;
			v += (ax << (i * 8));
			position++;
			if (position >= buff.length) {
				break;
			}
		}

		return v;
	}

	/*
	 * 计算hash值，返回是非负数
	 */
	public long hash(byte[] pKey) {
		int nKeyLen = pKey.length;
		int v = 0xdeadbeef + nKeyLen;
		Int a = new Int(v);
		Int b = new Int(v);
		Int c = new Int(v);

		int index = 0;
		while (nKeyLen > 12) {
			a.add(getInt(pKey, index));
			b.add(getInt(pKey, index + 4));
			c.add(getInt(pKey, index + 8));

			index += 12;
			mix(a, b, c);
			nKeyLen -= 12;
		}

		switch (nKeyLen) {
			case 12: {
				c.add(getInt(pKey, index + 8));
				b.add(getInt(pKey, index + 4));
				a.add(getInt(pKey, index));
				break;
			}
			case 11: {
				c.add(getInt(pKey, index + 8) & 0xffffff);
				b.add(getInt(pKey, index + 4));
				a.add(getInt(pKey, index));
				break;
			}
			case 10: {
				c.add(getInt(pKey, index + 8) & 0xffff);
				b.add(getInt(pKey, index + 4));
				a.add(getInt(pKey, index));
				break;
			}
			case 9: {
				c.add(getInt(pKey, index + 8) & 0xff);
				b.add(getInt(pKey, index + 4));
				a.add(getInt(pKey, index));
				break;
			}
			case 8: {
				b.add(getInt(pKey, index + 4));
				a.add(getInt(pKey, index));
				break;
			}
			case 7: {
				b.add(getInt(pKey, index + 4) & 0xffffff);
				a.add(getInt(pKey, index));
				break;
			}
			case 6: {
				b.add(getInt(pKey, index + 4) & 0xffff);
				a.add(getInt(pKey, index));
				break;
			}
			case 5: {
				b.add(getInt(pKey, index + 4) & 0xff);
				a.add(getInt(pKey, index));
				break;
			}
			case 4: {
				a.add(getInt(pKey, index));
				break;
			}
			case 3: {
				a.add(getInt(pKey, index) & 0xffffff);
				break;
			}
			case 2: {
				a.add(getInt(pKey, index) & 0xffff);
				break;
			}
			case 1: {
				a.add(getInt(pKey, index) & 0xff);
				break;
			}
			case 0: {
				return c.getValue() & 0xFFFFFFFFL;
			}
		}

		finalx(a, b, c);
		return c.getValue() & 0xFFFFFFFFL;
	}
	public long hash(String key) {
		return hash(key.getBytes());
	}
	
	private class Int {
		private int value;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public Int(int v) {
			this.value = v;
		}

		public void add(int v) {
			this.value += v;
		}

		@Override
		public String toString() {
			return String.valueOf(value);
		}
	}
}
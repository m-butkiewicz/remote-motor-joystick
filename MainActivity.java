// central -> peripheral
// https://www.youtube.com/watch?v=x1y4tEHDwk0

public class MainActivity extends Activity implements BluetoothAdapter.LescanCallback {
	private static final String DEVICE_NAME = "SensorTag";
	
	private static final UUID HUMIDITY_SERVICE = UUID.fromString("f000aa20-0451-4000-b000-000000000000");
	private static final UUID HUMIDITY_DATA_CHAR = UUID.fromString("f000aa21-0451-4000-b000-000000000000");
	private static final UUID HUMIDITY_CONFIG_CHAR = UUID.fromString("f000aa22-0451-4000-b000-000000000000");
	
	private static final UUID PRESSURE_SERVICE = UUID.fromString("f000aa40-0451-4000-b000-000000000000");
	private static final UUID PRESSURE_DATA_CHAR = UUID.fromString("f000aa41-0451-4000-b000-000000000000");
	private static final UUID PRESSURE_CONFIG_CHAR = UUID.fromString("f000aa42-0451-4000-b000-000000000000");
	private static final UUID PRESSURE_CAL_CHAR = UUID.fromString("f000aa43-0451-4000-b000-000000000000");
	
	private static final UUID CONFIG_DESCRIPTOR = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
	
	private BluetoothAdapter bluetoothAdapter;
	private SparseArray<BluetoothDevice> bluetoothDevices;
	private BluetoothGatt connectedGatt;
	
	private TextView temperature, humidity, pressure;
	private ProgressDialog progress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_main);
		setProgressBarIndeterminate(true);
		
		// display results
		// ...
		
		BluetoothManager manager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
		bluetoothAdapter = manager.getAdapter();
		
		bluetoothDevices = new SparseArray<BluetoothDevice();
		
		progress = new ProgressDialog(this);
		progress.setIndeterminate(true);
		progress.setCancelable(false);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (bluetoothAdapter == null || bluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivity(enableIntent);
			finish();
			return;
		}
		
		if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
			Toast.makeText(this, "No LE Support", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		
		clearDisplayValues();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		progress.dismiss();
		handler.removeCallbacks(stopRunnable);
		handler.removeCallbacks(startRunnable);
		bluetoothAdapter.stopLeScan(this);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		if (connectedGatt != null) {
			connectedGatt.disconnect();
			connectedGatt = null;
		}
	}
	
	@Override 
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		for (int i=0; i < bluetoothDevices.size(); i++) {
			BluetoothDevice device = bluetoothDevices.valueAt(i);
			manu.add(0, bluetoothDevices.keyAt(i), 0, device.getName());
		}
		
		return true;
	}
	
	@Override
	public boolean onOptionsMenuSelected(menuItem item) {
		switch (item.getItemId()) {
			case R.id.action_scan:
				bluetoothDevices.clear();
				startScan();
				return true;
			default:
				BluetoothDevice device = bluetoothDevices.get(item.getItemId());
				connectedGatt = device.connectGatt(this, true, gattCallback);
				handler.sendMessage(Message.obtain(null, MSG_PROGRESS, "Connecting to " + device.getName()+"...");
				return super.onOptionsItemSelected(item);
		}
	}
	
	private void clearDisplayValues() {
		// ...
	}
	
	private Runnable stopRunnable = () -> { stopScan(); };
	private Runnable startRunnable = () -> { startScan(); };
	
	private void startScan() {
		bluetoothAdapter.startLeScan(this);
		setProgressBarIndeterminateVisibility(true);
		handler.postDelayed(stopRunnable, 2500);
	}
	
	private void stopScan() {
		bluetoothAdapter.stopLeScan(this);
		setProgressBarIndeterminateVisibility(false);
	}
	
	@Override
	public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
		if (DEVICE_NAME.equals(device.getName())) {
			bluetoothDevices.put(device.hashCode(), device);
			invalidateOptionsMenu();
		}
	}
	
	private BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
		
		private int state = 0;
		private void reset() { state = 0; }
		private void advance() { state++; }
		
		private void enableNextSensor(Bluetooth gatt) {
			BluetoothGattCharacteristic characteristic;
			switch (state) {
				case 0:
					characteristic = gatt.getService(PRESSURE_SERVICE)
						.getCharacteristic(PRESSURE_CONFIG_CHAR);
					characteristic.setValue(new byte[] {0x02});
					break;
				case 1:
					characteristic = gatt.getService(PRESSURE_SERVICE)
						.getCharacteristic(PRESSURE_CONFIG_CHAR);
					characteristic.setValue(new byte[] {0x01});
					break;
				case 2:
					characteristic = gatt.getService(HUMIDITY_SERVICE)
						.getCharacteristic(HUMIDITY_CONFIG_CHAR);
					characteristic.setValue(new byte[] {0x01});
					break;
				default:
					handler.sendEmptyMessage(MSG_DISMISS);
					return;
			}
			
			gatt.writeCharacteristic(characteristic);
		}
	}
}
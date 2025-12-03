import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SoundPanel extends JPanel{
	private SoundSetting soundSetting = new SoundSetting();
	private JLabel title = new JLabel("소리 설정", JLabel.CENTER);
	private JDialog sound = null;
	private JSlider BGMSlider = null;
	private JSlider SFXSlider = null;
	private Font mainFont = new Font("Galmuri9", Font.BOLD, 40);
	private Font subFont = new Font("Galmuri9", Font.BOLD, 30);
	private ImageIcon bgIcon = new ImageIcon("image/background.jpg");
	private Image bgImg = bgIcon.getImage();
	
	public SoundPanel() {
		BGMSlider = new JSlider(0,100,soundSetting.bgmVolume);
		SFXSlider = new JSlider(0,100,soundSetting.sfxVolume);
		
		setLayout(new GridLayout(3,1));
		setSize(500, 400);
		
		title.setFont(mainFont);
		title.setForeground(Color.WHITE);
		add(title);
		
		JPanel BGMPanel = createSliderPanel("배경음",BGMSlider);
		add(BGMPanel);
		
		JPanel SFXPanel = createSliderPanel("효과음",SFXSlider);
		add(SFXPanel);
		
		addKeyListener(new ExitESC());
		setFocusable(true);
		requestFocus();
		
		BGMSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				int value = source.getValue();
				soundSetting.bgmVolume = value;
				System.out.println("bgm:" + soundSetting.bgmVolume);
			}
		});
		
		SFXSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				int value = source.getValue();
				soundSetting.sfxVolume = value;
				System.out.println("sfx:" + soundSetting.sfxVolume);
			}
		});
		
		makeSoundDialog();
	}
	public JPanel createSliderPanel(String text, JSlider slider) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,20));
		panel.setOpaque(false);
		JLabel label = new JLabel(text);
		label.setFont(subFont);
		label.setForeground(Color.WHITE);
		label.setSize(200,100);
		
		slider.setOpaque(false);
		slider.setMajorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setForeground(Color.WHITE);
		slider.setSize(200,100);
		
		panel.add(label);
		panel.add(slider);
		
		return panel;
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
	}
	
	public void makeSoundDialog() { // Dialog 생성 메소드
		sound = new JDialog(); // Dialog 객체 생성

		sound.setTitle("사운드");
		sound.setSize(500, 500);
		sound.setLocation(700, 150);
		
		sound.setContentPane(this); // soundPanel 넣기
		sound.setModal(true); // 다른 작업 못하게 modal 설정
		sound.setVisible(true); // 활성화
	}
	
	class ExitESC extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			int code = e.getKeyCode();
			switch (code) {
			case KeyEvent.VK_ESCAPE:
				sound.setVisible(false);
			}
		}
	}
}

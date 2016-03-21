import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class TicTacToe extends JFrame{
	
	private static final String TITLE="Tic Tac Toe";
	private static final int WIDTH=60*3*3;
	private static final int HEIGHT=60*3*4;
	private JButton[][] cells;
	private JPanel p;
	private JPanel[] panels;
	public static final Random r=new Random();
	private CellButtonHandler[][] cellHandlers;
	private ExitButtonHandler exitHandler;
	private InitButtonHandler initHandler;
	private JButton exitButton;
	private JButton initButton;
	private JLabel result;
	private JLabel[] smallResults;
	private int oWins;
	private int xWins;
	private int tournamentNum;
	
	private JButton nextRoundButton;
	private NextRoundButtonHandler nextRoundHandler;
	
	private JPanel gameOverPanel;
	
	private boolean gameOver;
	private boolean noughts;
	
	private int previousNum;
	
	JPanel exitpanel = new JPanel();
	JPanel initpanel = new JPanel();
	
	private boolean firstMove=true;
	
	public TicTacToe() {
		
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		result=new JLabel("noughts", SwingConstants.CENTER);
		result.setFont(new Font("Chalkduster", Font.PLAIN, 30));
		
		JPanel exitpanel = new JPanel();
		exitpanel.setLayout(new GridLayout(1,1));
		exitButton=new JButton("EXIT");
		exitButton.setFont(new Font("Chalkduster", Font.PLAIN, 40));
		exitHandler=new ExitButtonHandler();
		exitButton.addActionListener(exitHandler);
		exitpanel.add(exitButton);
		
		JPanel initpanel = new JPanel();
		initpanel.setLayout(new GridLayout(1,1));
		initButton=new JButton("RESTART");
		initButton.setFont(new Font("Chalkduster", Font.PLAIN, 40));
		initHandler=new InitButtonHandler();
		initButton.addActionListener(initHandler);
		initpanel.add(initButton);
		
		gameOver=false;
		noughts=true;
		
		p = new JPanel();
		p.setLayout(new GridLayout(4,3));
		p.setSize(WIDTH, HEIGHT);
		add(p);
		
		panels = new JPanel[9];
		cells = new JButton[9][9];
		cellHandlers = new CellButtonHandler[9][9];
		smallResults = new JLabel[9];
		for (int i=0; i<9; i++) {
			smallResults[i] = new JLabel(Integer.toString(i));
		}
		for (int i=0; i<9; i++) {
			panels[i] = new JPanel();
			panels[i].setBackground(Color.BLUE);
			panels[i].setBorder(new LineBorder(Color.BLUE, 5));
			p.add(panels[i]);
			panels[i].setLayout(new GridLayout(3,3));
			//Color c=new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256),r.nextInt(256));
			for (int j=0; j<9; j++) {
				cells[i][j] = new JButton("j");
				cells[i][j].setFont(new Font("Courier New", Font.BOLD, 20));
				cells[i][j].setForeground(Color.GRAY);
				cells[i][j].setBackground(Color.ORANGE);
				cells[i][j].setOpaque(true);
				panels[i].add(cells[i][j]);
				cellHandlers[i][j]=new CellButtonHandler();
				cells[i][j].addActionListener(cellHandlers[i][j]);
			}
		}
		p.add(initpanel);
		p.add(result);
		p.add(exitpanel);
		
		nextRoundButton=new JButton("Next Round");
		//System.out.println("Button");
		nextRoundHandler=new NextRoundButtonHandler();
		nextRoundButton.addActionListener(nextRoundHandler);
		
		gameOverPanel = new JPanel();
		
		xWins = 0;
		oWins = 0;
		
		tournamentNum = 0;
		
		init();
	}
	
	public void init() {
		
		firstMove =true;
		
		result=new JLabel("noughts", SwingConstants.CENTER);
		result.setFont(new Font("Chalkduster", Font.PLAIN, 30));
		result.setForeground(Color.GRAY);
		
		JPanel exitpanel = new JPanel();
		exitpanel.setLayout(new GridLayout(1,1));
		exitButton=new JButton("EXIT");
		exitHandler=new ExitButtonHandler();
		exitButton.addActionListener(exitHandler);
		exitpanel.add(exitButton);
		
		JPanel initpanel = new JPanel();
		initpanel.setLayout(new GridLayout(1,1));
		initButton=new JButton("RESTART");
		initHandler=new InitButtonHandler();
		initButton.addActionListener(initHandler);
		initpanel.add(initButton);
		gameOver=false;
		noughts=true;
		
		p.removeAll();
		p.setLayout(new GridLayout(4,3));
		p.setSize(WIDTH, HEIGHT);
		
		gameOverPanel.removeAll();

		for (int i=0; i<9; i++) {
			smallResults[i] = new JLabel(Integer.toString(i));
		}
		
		for (int i=0; i<9; i++) {
			panels[i] = new JPanel();
			panels[i].setBackground(Color.BLUE);
			panels[i].setBorder(new LineBorder(Color.BLUE, 5));
			p.add(panels[i]);
			panels[i].setLayout(new GridLayout(3,3));
			//Color c=new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256),r.nextInt(256));
			for (int j=0; j<9; j++) {
				cells[i][j] = new JButton("j");
				cells[i][j].setFont(new Font("Chalkduster", Font.PLAIN, 40));
				cells[i][j].setForeground(Color.GRAY);
				cells[i][j].setBackground(Color.ORANGE);
				cells[i][j].setOpaque(true);
				panels[i].add(cells[i][j]);
				cellHandlers[i][j]=new CellButtonHandler();
				cells[i][j].addActionListener(cellHandlers[i][j]);
			}
		}
		p.add(initpanel);
		p.add(result);
		p.add(exitpanel);
		
		for(int i=0; i<9; i++)
		{
			for (int j=0 ; j<9; j++) {
				String ch=(String)(""+ (j+1) );
				cells[i][j].setText(""+ch);
			}
			
		}
		
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TicTacToe t = new TicTacToe();
	}
	
	public boolean checkWinner(int i)
	{
		if(cells[i][0].getText().equals(cells[i][1].getText()) && cells[i][1].getText().equals(cells[i][2].getText()))
		{
			return true;
		}
		else if(cells[i][3].getText().equals(cells[i][4].getText()) && cells[i][4].getText().equals(cells[i][5].getText()))
		{
			return true;
		}
		else if(cells[i][6].getText().equals(cells[i][7].getText()) && cells[i][7].getText().equals(cells[i][8].getText()))
		{
			return true;
		}
		else if(cells[i][0].getText().equals(cells[i][3].getText()) && cells[i][3].getText().equals(cells[i][6].getText()))
		{
			return true;
		}
		else if(cells[i][1].getText().equals(cells[i][4].getText()) && cells[i][4].getText().equals(cells[i][7].getText()))
		{
			return true;
		}
		else if(cells[i][2].getText().equals(cells[i][5].getText()) && cells[i][5].getText().equals(cells[i][8].getText()))
		{
			return true;
		}
		else if(cells[i][0].getText().equals(cells[i][4].getText()) && cells[i][4].getText().equals(cells[i][8].getText()))
		{
			return true;
		}
		else if(cells[i][2].getText().equals(cells[i][4].getText()) && cells[i][4].getText().equals(cells[i][6].getText()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean checkUltimateWinner() {
		if (smallResults[0].getText().equals(smallResults[1].getText()) && smallResults[1].getText().equals(smallResults[2].getText()))
			return true;
		if (smallResults[3].getText().equals(smallResults[4].getText()) && smallResults[4].getText().equals(smallResults[5].getText()))
			return true;
		if (smallResults[6].getText().equals(smallResults[7].getText()) && smallResults[7].getText().equals(smallResults[8].getText()))
			return true;
		if (smallResults[0].getText().equals(smallResults[3].getText()) && smallResults[3].getText().equals(smallResults[6].getText()))
			return true;
		if (smallResults[1].getText().equals(smallResults[4].getText()) && smallResults[4].getText().equals(smallResults[7].getText()))
			return true;
		if (smallResults[2].getText().equals(smallResults[5].getText()) && smallResults[5].getText().equals(smallResults[8].getText()))
			return true;
		if (smallResults[0].getText().equals(smallResults[4].getText()) && smallResults[4].getText().equals(smallResults[8].getText()))
			return true;
		if (smallResults[2].getText().equals(smallResults[4].getText()) && smallResults[4].getText().equals(smallResults[6].getText()))
			return true;
		else
			return false;
	}
	
	private class CellButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//If game over, ignore
			if(gameOver)
			{
				return;
			}
			
			//Get button pressed
			JButton pressed=(JButton)(e.getSource());
			
			//Get text of button
			String text=pressed.getText();
			
			//If noughts or crosses, ignore
			if(text.equals("O") || text.equals("X"))
			{
				return;
			}
			
			int num=0;
			for (int i=0; i<9; i++) {
				for (int j=0; j<9; j++) {
					if (cells[i][j].equals(pressed))
						num =  i;
				}
			}
			//System.out.println(num != previousNum);
			//System.out.println(smallResults[previousNum].getText().equals(previousNum+""));
			
			if (firstMove==false){
					if (num != previousNum && (smallResults[previousNum].getText().equals(previousNum+"")))
						return;}
			previousNum = num;
			
			//System.out.println(num != previousNum);
			
			//Add nought or cross
			if(noughts)
			{
				pressed.setText("O");

			}
			else
			{
				pressed.setText("X");
			}
			
			
			
			//Check winner
			if(checkWinner(num))
			{
					//End of game
					//gameOver=true;
					
					//Display winner message
				if(noughts)
				{
					//Timer timer = new Timer(10, this);
					//timer.setInitialDelay(10);
					//timer.start(); 
					smallResults[num] = new JLabel("O", SwingConstants.CENTER);
					smallResults[num].setFont(new Font("Chalkduster", Font.PLAIN, 48));
					smallResults[num].setForeground(Color.GRAY);
					smallResults[num].setSize(100,100);
					smallResults[num].setBackground(Color.green);
					smallResults[num].setOpaque(false);
						//res.setBackground(Color.white);
					panels[num].removeAll();
					panels[num].setLayout(new GridLayout(1,1));
					panels[num].setOpaque(false);
					panels[num].add(smallResults[num]);
					panels[num].revalidate();
				}
				else
				{
					smallResults[num] = new JLabel("X", SwingConstants.CENTER);
					smallResults[num].setFont(new Font("Chalkduster", Font.PLAIN, 48));
					smallResults[num].setForeground(Color.GRAY);
					smallResults[num].setSize(100,100);
					//smallResults[num].setBackground(Color.green);
					smallResults[num].setOpaque(false);
						//res.setBackground(Color.white);
					panels[num].removeAll();
					panels[num].setLayout(new GridLayout(1,1));
					panels[num].setOpaque(false);
					panels[num].add(smallResults[num]);
					panels[num].revalidate();
				}
			}
			else if (allSpotsTaken(cells[num])) {
				smallResults[num] = new JLabel("Tie", SwingConstants.CENTER);
				smallResults[num].setFont(new Font("Chalkduster", Font.PLAIN, 48));
				smallResults[num].setForeground(Color.GRAY);
				smallResults[num].setSize(100,100);
				smallResults[num].setBackground(Color.green);
				smallResults[num].setOpaque(false);
					//res.setBackground(Color.white);
				panels[num].removeAll();
				panels[num].setLayout(new GridLayout(1,1));
				panels[num].setOpaque(false);
				panels[num].add(smallResults[num]);
				panels[num].revalidate(); }
			else
			{
					//Change player
				noughts=!noughts;
	
					//Display player message
				if(noughts)
				{
					result.setText("Noughts");
				}
				else
				{
					result.setText("Crosses");
				}
			}
			
			if (checkUltimateWinner()) {
				tournamentCount();
				//System.out.println("Game Over");
				gameOver = true;
				if (noughts) {
					gameOver("Noughts");
				}
				else
					gameOver("Crosses");
					result.setText("Crosses Win!");
			}
			else if (allSpotsTaken(smallResults))
				gameOver("Tie");
			
			if (firstMove==true) {
				firstMove = false;}
		}
		
	}
	
	public void gameOver(String str) {
		gameOverPanel.setLayout(new GridLayout(5,1));
		//add(initButton);
		//add(exitButton);
		JLabel gameOverLabel = new JLabel(str, SwingConstants.CENTER);
		gameOverLabel.setFont(new Font("Chalkduster", Font.PLAIN, 20));
		gameOverLabel.setBackground(Color.WHITE);
		gameOverLabel.setForeground(Color.ORANGE);
		if (tournamentNum <5 && xWins <3 && oWins <3) {
			if (str.equals("Tie"))
				gameOverLabel.setText("Game Over! " + "That is a Tie!");
			else if (noughts) {
				gameOverLabel.setText("Game Over! " + str + " Wins This Round!");
			}
			else
				gameOverLabel.setText("Game Over! "+ str +" Wins This Round!");
		}
		else if (tournamentNum ==5 || xWins== 3 || oWins == 3){
			if (oWins == 3) {
				gameOverLabel.setText("Game Over! " + str + " Wins This Tournament!");
			}
			else if (xWins == 3) {
				gameOverLabel.setText("Game Over! "+ str +" Wins This Tournament!");
				gameOverLabel.setFont(new Font("Chalkduster", Font.PLAIN, 20));
			}
			else
				gameOverLabel.setText("Game Over! "+ "The Tournament is a Tie!");
		}
		
		if (tournamentNum == 5 || oWins == 3 || xWins == 3){
			nextRoundButton.setText("New Tournament");
			tournamentNum = 0;
		}
		
		JPanel opanel = new JPanel();
		opanel.setLayout(new GridLayout(1,1));
		JLabel olabel = new JLabel("Noughts has won " + oWins + " rounds.", SwingConstants.CENTER);
		olabel.setFont(new Font("Chalkduster", Font.PLAIN, 24));
		olabel.setForeground(Color.BLUE);
		opanel.add(olabel);
		
		JPanel xpanel = new JPanel();
		xpanel.setLayout(new GridLayout(1,1));
		JLabel xlabel = new JLabel("Crosses has won " + xWins + " rounds.", SwingConstants.CENTER);
		xlabel.setFont(new Font("Chalkduster", Font.PLAIN, 24));
		xlabel.setForeground(Color.ORANGE);
		xpanel.add(xlabel);
		
		JPanel roundsNumPanel = new JPanel();
		roundsNumPanel.setLayout(new GridLayout(1,1));
		if (tournamentNum !=5 && xWins!= 3 && oWins != 3) {
			JLabel roundsNumLabel = new JLabel((5-tournamentNum) + " more rounds to go!" + " Win 3 out of 5 games!", SwingConstants.CENTER);
			roundsNumLabel.setFont(new Font("Chalkduster", Font.PLAIN, 20));
			roundsNumLabel.setForeground(Color.BLUE);
			roundsNumPanel.add(roundsNumLabel);
		}
	
		p.removeAll();
		JPanel labelpanel = new JPanel();
		JPanel buttonpanel = new JPanel();
		labelpanel.setLayout(new GridLayout(1,1));
		//labelpanel.setBackground();
		buttonpanel.setLayout(new GridLayout(1,1));
		//buttonpanel.setBackground(Color.ORANGE);
		labelpanel.add(gameOverLabel);
		buttonpanel.add(nextRoundButton);
		gameOverPanel.add(labelpanel);
		gameOverPanel.add(opanel);
		gameOverPanel.add(xpanel);
		gameOverPanel.add(roundsNumPanel);
		gameOverPanel.add(buttonpanel);
		add(gameOverPanel);
	}

	private class ExitButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	}

	private class InitButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			init();
		}
	}
	
	private class NextRoundButtonHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			init();
			//System.out.println("initiated");
		}
	}
	
	public boolean allSpotsTaken(JButton[] a) {
		for (int i=0; i< a.length; i++) {
			if ((!a[i].getText().equals("O")) && (!a[i].getText().equals("X")))
					return false;
		}
		return true;
	}
	
	public boolean allSpotsTaken(JLabel[] a) {
		for (int i=0; i< a.length; i++) {
			if ((!a[i].getText().equals("O")) && (!a[i].getText().equals("X")))
					return false;
		}
		return true;
	}
	
	public void tournamentCount() {
		if (noughts)
			oWins+=1;
		else
			xWins+=1;
		
		tournamentNum+=1;
	}
	
}
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.table.DefaultTableModel;

public class Earth extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Earth() {
		initComponents();
		readMemory();
		cleanRegister();
		readRegister();
	}

	private void initComponents() {

		controller = new JPanel();
		compile = new JButton();
		run = new JButton();
		step = new JButton();
		edit = new JButton();
		newFile = new JButton();
		regisrer = new JPanel();
		jScrollPane2 = new JScrollPane();
		regisrerTable = new JTable();
		content = new JPanel();
		jScrollPane1 = new JScrollPane();
		instructions = new JTextArea();
		jScrollPane3 = new JScrollPane();
		memory = new JTextArea();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Earth For MIBS Instruction");
		setBackground(new java.awt.Color(0, 0, 0));

		controller.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		compile.setText("Compile");
		compile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				compileActionPerformed(evt);
			}
		});

		run.setText("Run");
		run.setEnabled(false);
		run.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				runActionPerformed(evt);
			}
		});

		step.setText("Step");
		step.setEnabled(false);
		step.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				stepActionPerformed(evt);
			}
		});

		edit.setText("Edit Instructions");
		edit.setEnabled(false);
		edit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editActionPerformed(evt);
			}
		});

		newFile.setText("new File");
		newFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newFileActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout controllerLayout = new javax.swing.GroupLayout(controller);
		controller.setLayout(controllerLayout);
		controllerLayout.setHorizontalGroup(controllerLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(controllerLayout.createSequentialGroup().addContainerGap().addComponent(compile)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(run)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(step)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(edit)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(newFile)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		controllerLayout
				.setVerticalGroup(controllerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(controllerLayout.createSequentialGroup().addContainerGap().addGroup(controllerLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(compile)
								.addComponent(run).addComponent(step).addComponent(edit).addComponent(newFile))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		regisrerTable.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		regisrerTable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { "$zero", "0", "0" }, { "$a0", "1", "0" }, { "$a1", "2", "0" }, { "$a2", "3", "0" },
						{ "$s0", "4", "0" }, { "$s1", "5", "0" }, { "$s2", "6", "0" }, { "$s3", "7", "0" },
						{ "$s4", "8", "0" }, { "$t0", "9", "0" }, { "$t1", "10", "0" }, { "$t2", "11", "0" },
						{ "$t3", "12", "0" }, { "$t4", "13", "0" }, { "$ra", "14", "0" }, { "$v", "15", "0" } },
				new String[] { "Name", "Number", "Value" }));
		regisrerTable.setAutoscrolls(false);
		regisrerTable.setDropMode(javax.swing.DropMode.ON);
		regisrerTable.setEnabled(false);
		regisrerTable.setFocusable(false);
		regisrerTable.setRequestFocusEnabled(false);
		regisrerTable.setRowHeight(25);
		regisrerTable.getTableHeader().setResizingAllowed(false);
		regisrerTable.getTableHeader().setReorderingAllowed(false);
		jScrollPane2.setViewportView(regisrerTable);
		if (regisrerTable.getColumnModel().getColumnCount() > 0) {
			regisrerTable.getColumnModel().getColumn(0).setResizable(false);
			regisrerTable.getColumnModel().getColumn(1).setResizable(false);
			regisrerTable.getColumnModel().getColumn(2).setResizable(false);
		}

		javax.swing.GroupLayout regisrerLayout = new javax.swing.GroupLayout(regisrer);
		regisrer.setLayout(regisrerLayout);
		regisrerLayout.setHorizontalGroup(regisrerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						regisrerLayout.createSequentialGroup()
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 259,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		regisrerLayout.setVerticalGroup(regisrerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(regisrerLayout.createSequentialGroup().addComponent(jScrollPane2,
						javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(0,
								0, Short.MAX_VALUE)));

		jScrollPane1.setFocusCycleRoot(true);
		jScrollPane1.setFocusTraversalPolicyProvider(true);

		instructions.setColumns(20);
		instructions.setRows(5);
		instructions.setFocusCycleRoot(true);
		instructions.setFocusTraversalPolicyProvider(true);
		jScrollPane1.setViewportView(instructions);

		javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(content);
		content.setLayout(contentLayout);
		contentLayout.setHorizontalGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE));
		contentLayout.setVerticalGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING,
						javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE));

		memory.setEditable(false);
		memory.setColumns(20);
		memory.setRows(5);
		jScrollPane3.setViewportView(memory);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jScrollPane3))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(regisrer,
						javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGroup(layout.createSequentialGroup().addComponent(controller, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup()
				.addComponent(controller, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
						javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addComponent(content, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 142,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addComponent(regisrer, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		getAccessibleContext().setAccessibleName("Earth");

		pack();
	}

	boolean good = true;
	ArrayList<String> program = new ArrayList<String>();

	private void compileActionPerformed(java.awt.event.ActionEvent evt) {
		good = true;
		cleanRegister();
		instructions.setText(compile(instructions.getText()));
		if (good) {
			program = fillProgram(instructions.getText());
			if(program.size()>0){
				run.setEnabled(true);
				step.setEnabled(true);
				compile.setEnabled(false);
				instructions.setEditable(false);
				edit.setEnabled(true);
				readRegister();
				Datapath.PC=0;
				Datapath.program = program;
			}
		}
		good = true;
	}

	private ArrayList<String> fillProgram(String text) {
		ArrayList<String> instructions = new ArrayList<String>();
		String[] inst = text.split("\n");
		for (int i = 0; i < inst.length; i++) {
			String s = inst[i].trim().toLowerCase();
			String[] in = s.split("#");
			String string = remomveSpace(putInstruction(in[0]));
			if (!string.isEmpty()&&string.length()==16)
				instructions.add(string);
		}
		return instructions;
	}

	private String putInstruction(String Instruction) {
		String ope = "";
		int i = 0;
		while (i < Instruction.length()) {
			if (Instruction.charAt(i) == ' ' && !ope.equals("")) {
				i++;
				break;
			} else if (Instruction.charAt(i) == ' ') {
			} else {
				ope += Instruction.charAt(i);
			}
			i++;
		}
		Instruction = remomveSpace(Instruction.substring(i, Instruction.length()));
		String[] reg = Instruction.split(",");
		switch (ope) {
		case "add": {
			String oppe = "0000";
			String rs = Integer.toBinaryString(getRegisterNumber(reg[1]));
			while (rs.length() < 4)
				rs = "0" + rs;
			String rt = Integer.toBinaryString(getRegisterNumber(reg[2]));
			while (rt.length() < 4)
				rt = "0" + rt;
			String rd = Integer.toBinaryString(getRegisterNumber(reg[0]));
			while (rd.length() < 4)
				rd = "0" + rd;
			return oppe + rs + rt+ rd ;
		}
		case "addi": {
			String oppe = "0001";
			String rs = Integer.toBinaryString(getRegisterNumber(reg[1]));
			while (rs.length() < 4)
				rs = "0" + rs;
			String rt = Integer.toBinaryString(Integer.parseInt(reg[2]));
			while (rt.length() < 4) {
				if (Integer.parseInt(reg[2]) >= 0)
					rt = "0" + rt;
				else
					rt = "1" + rt;
			}
			String rd = Integer.toBinaryString(getRegisterNumber(reg[0]));
			while (rd.length() < 4)
				rd = "0" + rd;
			return oppe + rs + rd + rt;
		}
		case "div": {
			String oppe = "0010";
			String rs = Integer.toBinaryString(getRegisterNumber(reg[1]));
			while (rs.length() < 4)
				rs = "0" + rs;
			String rt = Integer.toBinaryString(getRegisterNumber(reg[2]));
			while (rt.length() < 4)
				rt = "0" + rt;
			String rd = Integer.toBinaryString(getRegisterNumber(reg[0]));
			while (rd.length() < 4)
				rd = "0" + rd;
			return oppe + rs + rt+ rd;
		}
		case "multi": {
			String oppe = "0011";
			String rs = Integer.toBinaryString(getRegisterNumber(reg[1]));
			while (rs.length() < 4)
				rs = "0" + rs;
			String rt = Integer.toBinaryString(Integer.parseInt(reg[2]));
			while (rt.length() < 4) {
				if (Integer.parseInt(reg[2]) >= 0)
					rt = "0" + rt;
				else
					rt = "1" + rt;
			}
			String rd = Integer.toBinaryString(getRegisterNumber(reg[0]));
			while (rd.length() < 4)
				rd = "0" + rd;
			return oppe + rs + rd + rt;
		}
		case "sub": {
			String oppe = "0100";
			String rs = Integer.toBinaryString(getRegisterNumber(reg[1]));
			while (rs.length() < 4)
				rs = "0" + rs;
			String rt = Integer.toBinaryString(getRegisterNumber(reg[2]));
			while (rt.length() < 4)
				rt = "0" + rt;
			String rd = Integer.toBinaryString(getRegisterNumber(reg[0]));
			while (rd.length() < 4)
				rd = "0" + rd;
			return oppe + rs + rt+ rd;
		}
		case "sll": {
			String oppe = "0101";
			String rs = Integer.toBinaryString(getRegisterNumber(reg[1]));
			while (rs.length() < 4)
				rs = "0" + rs;
			String rt = Integer.toBinaryString(Integer.parseInt(reg[2]));
			while (rt.length() < 4)
				rt = "0" + rt;
			String rd = Integer.toBinaryString(getRegisterNumber(reg[0]));
			while (rd.length() < 4)
				rd = "0" + rd;
			return oppe + rs + rd + rt;
		}
		case "slr": {
			String oppe = "0110";
			String rs = Integer.toBinaryString(getRegisterNumber(reg[1]));
			while (rs.length() < 4)
				rs = "0" + rs;
			String rt = Integer.toBinaryString(Integer.parseInt(reg[2]));
			while (rt.length() < 4)
				rt = "0" + rt;
			String rd = Integer.toBinaryString(getRegisterNumber(reg[0]));
			while (rd.length() < 4)
				rd = "0" + rd;
			return oppe + rs + rd + rt;
		}
		case "nand": {
			String oppe = "0111";
			String rs = Integer.toBinaryString(getRegisterNumber(reg[1]));
			while (rs.length() < 4)
				rs = "0" + rs;
			String rt = Integer.toBinaryString(getRegisterNumber((reg[2])));
			while (rt.length() < 4)
				rt = "0" + rt;
			String rd = Integer.toBinaryString(getRegisterNumber(reg[0]));
			while (rd.length() < 4)
				rd = "0" + rd;
			return oppe + rs + rt+ rd ;
		}
		case "lw": {
			String oppe = "1000";
			String[] rsRt = spliter(reg[1]);
			String rs = Integer.toBinaryString(getRegisterNumber(rsRt[1]));
			while (rs.length() < 4)
				rs = "0" + rs;
			String rt = Integer.toBinaryString(Integer.parseInt(rsRt[0]));
			while (rt.length() < 4) {
				if (Integer.parseInt(rsRt[0]) >= 0)
					rt = "0" + rt;
				else
					rt = "1" + rt;
			}
			String rd = Integer.toBinaryString(getRegisterNumber(reg[0]));
			while (rd.length() < 4)
				rd = "0" + rd;
			return oppe + rs + rd + rt;
		}
		case "sw": {
			String oppe = "1001";
			String[] rsRt = spliter(reg[1]);
			String rs = Integer.toBinaryString(getRegisterNumber(rsRt[1]));
			while (rs.length() < 4)
				rs = "0" + rs;
			String rt = Integer.toBinaryString(Integer.parseInt(rsRt[0]));
			while (rt.length() < 4) {
				if (Integer.parseInt(rsRt[0]) >= 0)
					rt = "0" + rt;
				else
					rt = "1" + rt;
			}
			String rd = Integer.toBinaryString(getRegisterNumber(reg[0]));
			while (rd.length() < 4)
				rd = "0" + rd;
			return oppe + rs + rd + rt;
		}
		case "beq": {
			String oppe = "1010";
			String rs = Integer.toBinaryString(getRegisterNumber(reg[1]));
			while (rs.length() < 4)
				rs = "0" + rs;
			String rt = Integer.toBinaryString(getRegisterNumber(reg[2]));
			while (rt.length() < 4)
				rt = "0" + rt;
			String rd = Integer.toBinaryString(getRegisterNumber(reg[0]));
			while (rd.length() < 4)
				rd = "0" + rd;
			return oppe + rs + rd + rt;
		}
		case "ben": {
			String oppe = "1011";
			String rs = Integer.toBinaryString(getRegisterNumber(reg[1]));
			while (rs.length() < 4)
				rs = "0" + rs;
			String rt = Integer.toBinaryString(getRegisterNumber(reg[2]));
			while (rt.length() < 4)
				rt = "0" + rt;
			String rd = Integer.toBinaryString(getRegisterNumber(reg[0]));
			while (rd.length() < 4)
				rd = "0" + rd;
			return oppe + rs + rd + rt;
		}
		case "jal": {
			String oppe = "1100";
			String address = Integer.toBinaryString(getRegisterNumber(reg[1]));
			while (address.length() < 12)
				address = "0" + address;
			return oppe + address;
		}
		case "j": {
			String oppe = "1101";
			String address = Integer.toBinaryString(getRegisterNumber(reg[1]));
			while (address.length() < 12)
				address = "0" + address;
			return oppe + address;
		}
		case "jr": {
			String oppe = "1110";
			String address = Integer.toBinaryString(getRegisterNumber(reg[1]));
			while (address.length() < 4)
				address = "0" + address;
			return oppe + address + "00000000";
		}
		case "blt": {
			String oppe = "1111";
			String rs = Integer.toBinaryString(getRegisterNumber(reg[1]));
			while (rs.length() < 4)
				rs = "0" + rs;
			String rt = Integer.toBinaryString(getRegisterNumber(reg[2]));
			while (rt.length() < 4)
				rt = "0" + rt;
			String rd = Integer.toBinaryString(getRegisterNumber(reg[0]));
			while (rd.length() < 4)
				rd = "0" + rd;
			return oppe + rs + rd + rt;
		}
		default: {
			return "";
		}
		}

	}

	private void editActionPerformed(java.awt.event.ActionEvent evt) {
		run.setEnabled(false);
		step.setEnabled(false);
		compile.setEnabled(true);
		cleanRegister();
		edit.setEnabled(false);
		instructions.setEditable(true);
	}

	private void newFileActionPerformed(java.awt.event.ActionEvent evt) {
		run.setEnabled(false);
		step.setEnabled(false);
		compile.setEnabled(true);
		edit.setEnabled(false);
		cleanRegister();
		instructions.setEditable(true);
		instructions.setText("");
	}

	private void runActionPerformed(java.awt.event.ActionEvent evt) {
		Datapath.handelPipeline();
		readRegister();
		readMemory();
		editActionPerformed(evt);
	}
	
	private void cleanRegister() {
		for(int i=0;i<Datapath.registers.length;i++){
			Datapath.registers[i]=0;
		}
	}

	private void readMemory() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("./src/DataMemory.txt"));
			String data = "";
			while (br.ready()) {
				data = data + br.readLine() + "\n";
			}
			br.close();
			String[] splittedData = data.split("\n");
			String s = "";
			for(int i=0;i<splittedData.length;i++){
				s+=splittedData[i]+" | ";
			}
			memory.setText(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void readRegister() {
		int[] registers = Datapath.registers;
		DefaultTableModel m = (DefaultTableModel) regisrerTable.getModel();
		for (int i = 0; i < registers.length; i++) {
			m.setValueAt(registers[i], i, 2);
		}
		regisrerTable.revalidate();
		regisrerTable.repaint();

	}

	private void stepActionPerformed(java.awt.event.ActionEvent evt) {
		Datapath.handelStepIns();
		readRegister();
		readMemory();
		if((Datapath.PC/2)==program.size())
			editActionPerformed(evt);
	}

	public static void main(String args[]) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Earth.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(Earth.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(Earth.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			Logger.getLogger(Earth.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Earth().setVisible(true);
			}
		});
	}

	private JButton compile;
	private JPanel content;
	private JPanel controller;
	private JButton edit;
	private JTextArea instructions;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JScrollPane jScrollPane3;
	private JTextArea memory;
	private JButton newFile;
	private JPanel regisrer;
	private JTable regisrerTable;
	private JButton run;
	private JButton step;

	public String compile(String instruction) {
		String[] inst = instruction.split("\n");
		String output = "";
		for (int i = 0; i < inst.length; i++) {
			output += checkInstruction(inst[i]) + "\n";
		}
		return output;
	}

	public String checkInstruction(String Instruction) {
		String ope = "";
		String inString = Instruction.toLowerCase();
		if (inString.isEmpty())
			return inString;
		if (inString.charAt(0) == '#')
			return inString;
		String[] afterRemoveComment = inString.split("#");
		String string = afterRemoveComment[0];
		if (remomveSpace(string) == "") {
			return inString;
		}
		int i = 0;
		while (i < string.length()) {
			if (string.charAt(i) == ' ' && !ope.equals("")) {
				i++;
				break;
			} else if (string.charAt(i) == ' ') {
			} else {
				ope += string.charAt(i);
			}
			i++;
		}
		string = string.substring(i, string.length());
		string = remomveSpace(string);
		String[] reg = string.split(",");
		switch (ope) {
		case "div":
		case "sub":
		case "nand":
		case "add": {
			if (reg.length != 3 || getRegisterNumber(reg[0]) == -1 || getRegisterNumber(reg[1]) == -1
					|| getRegisterNumber(reg[2]) == -1) {
				good = false;
				return inString + "  #Inncorrect Instruction";
			}
			return inString;
		}
		case "addi":
		case "multi":
		case "sll":
		case "slr":
		case "beq":
		case "ben":
		case "blt": {
			if (reg.length != 3 || getRegisterNumber(reg[0]) == -1 || getRegisterNumber(reg[1]) == -1
					|| !checkIfNumber(reg[2])) {
				good = false;
				return inString + "  #Inncorrect Instruction";
			}
			return inString;
		}
		case "lw":
		case "sw": {
			if (reg.length != 2 || getRegisterNumber(reg[0]) == -1) {
				good = false;
				return inString + "  #Inncorrect Instruction";
			}
			String[] rsRt = spliter(reg[1]);
			if (!checkIfNumber(rsRt[0]) || getRegisterNumber(rsRt[1]) == -1) {
				good = false;
				return inString + "  #Inncorrect Instruction";
			}
			return inString;
		}
		case "jal":
		case "j": {
			if (reg.length != 1 || !checkIfNumber(reg[0])) {
				good = false;
				return inString + "  #Inncorrect Instruction";
			}
			return inString;
		}
		case "jr": {
			if (reg.length != 1 || getRegisterNumber(reg[0]) == -1) {
				good = false;
				return inString + "  #Inncorrect Instruction";
			}
			return inString;
		}
		default: {
			good = false;
			return inString + "  #Inncorrect Instruction";
		}
		}

	}

	public String remomveSpace(String inString) {
		String out = "";
		for (int i = 0; i < inString.length(); i++) {
			if (inString.charAt(i) != ' ') {
				out += inString.charAt(i);
			}
		}
		return out;
	}

	public boolean checkIfNumber(String inString) {
		for (int i = 0; i < inString.length(); i++) {
			if (i == 0 & inString.charAt(0) == '-')
				i++;
			if (inString.charAt(i) > '9' || inString.charAt(i) < '0') {
				return false;
			}
		}
		return true;
	}

	public int getRegisterNumber(String s) {
		switch (s) {
		case "$zero": {
			return 0;
		}
		case "$a0": {
			return 1;
		}
		case "$a1": {
			return 2;
		}
		case "$a2": {
			return 3;
		}
		case "$s0": {
			return 4;
		}
		case "$s1": {
			return 5;
		}
		case "$s2": {
			return 6;
		}
		case "$s3": {
			return 7;
		}
		case "$s4": {
			return 8;
		}
		case "$t0": {
			return 9;
		}
		case "$t1": {
			return 10;
		}
		case "$t2": {
			return 11;
		}
		case "$t3": {
			return 12;
		}
		case "$t4": {
			return 13;
		}
		case "$ra": {
			return 14;
		}
		case "$v": {
			return 15;
		}
		default:
			return -1;
		}
	}

	public String[] spliter(String inString) {
		String[] out = new String[2];
		int c = 0;
		for (int i = 0; i < inString.length(); i++) {
			if (inString.charAt(i) == '(') {
				out[0] = inString.substring(0, i);
				c = i + 1;
			}
			if (inString.charAt(i) == ')')
				out[1] = inString.substring(c, i);
		}
		return out;
	}
}

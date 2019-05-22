import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Datapath 
{
	// Registers
	static int[] registers = new int[16];
	//pipeline registers FETDEC
	static String FETDEC = null;
	static int FePC=0;
	//pipeline registers DECEX
	static int[] DECEX = new int[18];
	//pipeline registers EXMEM
	static int[] EXMEM = new int[18];
	//pipeline registers MEMWRB
	static int[] MEMWRB = new int[6];
	// Control Signals
	static boolean Jump;		// Input to Mux5
	static boolean PCSrc;		// Input to Mux4 ANDed with zero flag
	static int MemToReg;	// Input to Mux3
	static boolean ALUSrc;		// Input to Mux2
	static boolean RegWrite;	// Input to Write Back
	static int RegDst;		// Input to Mux1
	static boolean MemWrite;	// Input to Data Memory Block
	static boolean MemRead;		// Input to Data Memory Block
	static boolean BNE;
	static boolean JR;
	static int ALUOp;			// Input to ALU Block
	// Instruction Decode Input
	static String instruction;
	// Instruction Decode Output
	static int regReadData1;	// Input to ALU source 1
	static int regReadData2;	// Input to ALU source 2 through Mux2	// Input to Data Memory writeData
	static int immediate;
	// ALU Block Output
	static boolean zero;	// Input to Mux4 ANDed with PCSrc
	static int aluResult;	// Input to Data Memory readAddress		// Input to Data Memory writeAddress	// Input to Register File writeData through Mux3
	// Data Memory Output
	static int memReadData;
	// Write Back Input
	static int writeRegister;
	// Wires
	static int wire1;
	static int wire2;
	static int wire3;
	static int wire4;
	static int wire5;
	static int wire6;
	static int wire7;
	static int wire8; //not shifted
	// General
	static int PC=0;
	static ArrayList<String> program= new ArrayList<String>();
	public static void main(String[] args) 
	{
		// ADD
		//			 Op rs   rt  rd
		String inst="1111001101000101";
		registers[3]=5;
		registers[4]=5;
		program.add(inst);
		 inst="0001101010100010";
		program.add(inst);
		 inst="0001101110110011";
		program.add(inst);
		 inst="0001110011000100";
		program.add(inst);
		handelPipeline();
	}
	public static void handelStepIns(){
		instructionFetch();
		setpipelinereg();
		instructionDecode();
		setpipelinereg();
		Execution();
		setpipelinereg();
		memoryAndLink();
		setpipelinereg();
		writeBack();
	}
	public static void handelPipeline(){
		int i =0;
		while(i<program.size()+5){
			if(i<program.size())
				i=PC/2;
			else
				i++;
			if(i>3){
				writeBack();
			}
			if(i>2&&i<program.size()+3){
				memoryAndLink();
			}
			if(i>1&&i<program.size()+2){
				Execution();	
			}
			if(i>0&&i<program.size()+1){
				instructionDecode();
			}
			if(i<program.size()){
				instructionFetch();
			}
			setpipelinereg();
		}
	}
	
	public static void setpipelinereg(){
		//set writeBack attri
		MEMWRB[0]=EXMEM[0];
		MEMWRB[1]=EXMEM[1];
		MEMWRB[2]=memReadData;
		MEMWRB[3]=EXMEM[7];
		MEMWRB[4]=EXMEM[9];
		MEMWRB[5]=EXMEM[16];
		//set memoryAndLink attri
		EXMEM[0]=DECEX[0];
		EXMEM[1]=DECEX[1];
		EXMEM[2]=DECEX[2];
		EXMEM[3]=DECEX[3];
		EXMEM[4]=DECEX[4];
		EXMEM[5]=wire2;
		EXMEM[6]=zero?1:0;
		EXMEM[7]=aluResult;
		EXMEM[8]=DECEX[10];
		EXMEM[9]=DECEX[12];
		EXMEM[10]=DECEX[9];
		EXMEM[11]=DECEX[13];
		EXMEM[12]=DECEX[14];
		EXMEM[13]=DECEX[15];
		EXMEM[14]=DECEX[16];
		EXMEM[15]=DECEX[17];
		EXMEM[16]=DECEX[8];
		EXMEM[17]=DECEX[9];
		//set Execution attri
		DECEX[0]=RegWrite?1:0;
		DECEX[1]=MemToReg;
		DECEX[2]=0;
		DECEX[3]=MemWrite?1:0;
		DECEX[4]=MemRead?1:0;
		DECEX[5]=0;
		DECEX[6]=ALUOp;
		DECEX[7]=ALUSrc?1:0;
		DECEX[8]=FePC;
		DECEX[9]=regReadData1;
		DECEX[10]=regReadData2;
		DECEX[11]=wire8;
		DECEX[12]=writeRegister;
		DECEX[13]=wire4;
		DECEX[14]=PCSrc?1:0;
		DECEX[15]=BNE?1:0;
		DECEX[16]=Jump?1:0;
		DECEX[17]=JR?1:0;
		//set instructionDecode attri
		FETDEC=instruction;
		FePC=wire1;
	}
	
	public static void instructionFetch ()
	{
		if(PC/2 < program.size())
		{
			instruction=program.get(PC/2);
			wire1=PC+2;
			PC =wire1;
		}
	}
	public static void instructionDecode ()
	{
		String OpCode="0";
		String readRegister1 ="0";
		String readRegister2 ="0";
		String readRegister3 ="0";
		int address =0;
		if(!FETDEC.equals("")){
			OpCode = FETDEC.substring(0, 4);
			readRegister1 = FETDEC.substring(4, 8);
			readRegister2 = FETDEC.substring(8, 12);
			readRegister3 = FETDEC.substring(12, 16);
			address =Integer.parseInt(FETDEC.substring(4,16), 2);
		}
		
		ControlUnit(OpCode);
		
		regReadData1=registers[Integer.parseInt(readRegister1, 2)];
		regReadData2=registers[Integer.parseInt(readRegister2, 2)];
		if(RegDst==1)
		{
			writeRegister=Integer.parseInt(readRegister3, 2);
		}
		else if(RegDst==0)
		{
			writeRegister=Integer.parseInt(readRegister2, 2);	
		}
		else
		{
			writeRegister=14;
		}
		wire8=Integer.parseInt(readRegister3, 2);
		
		String pcToString = Integer.toBinaryString(PC);
		while(pcToString.length()<32)
			pcToString="0"+pcToString;
		String x = Integer.toBinaryString(address)+"0";
		while(x.length()<13)
			x="0"+x;
		wire4= Integer.parseInt((pcToString.substring(0, 19)+x),2);
	}
	public static void ControlUnit (String OpCode)
	{
		switch (OpCode)
		{
			// LW
			case "1000":
			{
				JR=false;
				BNE=false;
				Jump=false;
				PCSrc=false;
				MemToReg=1;
				ALUSrc=true;
				RegWrite=true;
				RegDst=0;
				MemWrite=false;
				MemRead=true;
				ALUOp=0;	// ADD
				break;
			}
			// SW
			case "1001":
			{
				JR=false;
				BNE=false;
				Jump=false;
				PCSrc=false;
				ALUSrc=true;
				RegWrite=false;
				MemWrite=true;
				MemRead=false;
				ALUOp=0;	// ADD
				break;
			}
			// BEQ
			case "1010":
			{
				JR=false;
				BNE=false;
				Jump=false;
				PCSrc=true;
				ALUSrc=false;
				RegWrite=false;
				MemWrite=false;
				MemRead=false;
				ALUOp=2;	// SUB
				break;
			}
			// BNE
			case "1011":
			{
				JR=false;
				BNE=true;
				Jump=false;
				PCSrc=false;
				ALUSrc=false;
				RegWrite=false;
				MemWrite=false;
				MemRead=false;
				ALUOp=2;
				break;
			}
			// BLT
			case "1111":
			{
				JR=false;
				BNE=false;
				Jump=false;
				PCSrc=true;
				ALUSrc=false;
				RegWrite=false;
				MemWrite=false;
				MemRead=false;
				ALUOp=7;	// SUB
				break;
			}
			// I-Type //
			// addi
			case "0001":
			{
				JR=false;
				BNE=false;
				Jump=false;
				PCSrc=false;
				MemToReg=0;
				ALUSrc=true;
				RegWrite=true;
				RegDst=0;
				MemWrite=false;
				MemRead=false;
				ALUOp=0;
				break;
			}
			// multi
			case "0011":
			{
				JR=false;
				BNE=false;
				Jump=false;
				PCSrc=false;
				MemToReg=0;
				ALUSrc=true;
				RegWrite=true;
				RegDst=0;
				MemWrite=false;
				MemRead=false;
				ALUOp = 3;
				break;
			}
			// sll
			case "0101":
			{
				JR=false;
				BNE=false;
				Jump=false;
				PCSrc=false;
				MemToReg=0;
				ALUSrc=true;
				RegWrite=true;
				RegDst=0;
				MemWrite=false;
				MemRead=false;
				ALUOp = 5;
				break;
			}
			// slr
			case "0110":
			{
				JR=false;
				BNE=false;
				Jump=false;
				PCSrc=false;
				MemToReg=0;
				ALUSrc=true;
				RegWrite=true;
				RegDst=0;
				MemWrite=false;
				MemRead=false;
				ALUOp = 6;
				break;
			}
			// J-Type //
			// jal
			case "1100":
			{
				MemWrite=false;
				JR=false;
				MemToReg=2;
				RegDst=2;
				RegWrite=true;
				Jump=true;
				break;
			}
			// j
			case "1101":
			{
				MemWrite=false;
				RegWrite=false;
				JR=false;
				Jump=true;
				break;
			}
			// jr
			case "1110":
			{
				MemWrite=false;
				RegWrite=false;
				JR=true;
				Jump=true;
				break;
			}
			// R-Type //
			// Add 0000 | div 0010 | sub 0100 | nand 0111 |
			// Add
			case "0000":
			{
				JR=false;
				BNE=false;
				Jump=false;
				PCSrc=false;
				MemToReg=0;
				ALUSrc=false;
				RegWrite=true;
				RegDst=1;
				MemWrite=false;
				MemRead=false;
				ALUOp=0;
				break;
			}
			// div
			case "0010":
			{
				JR=false;
				BNE=false;
				Jump=false;
				PCSrc=false;
				MemToReg=0;
				ALUSrc=false;
				RegWrite=true;
				RegDst=1;
				MemWrite=false;
				MemRead=false;
				ALUOp=4;
				break;
			}
			// sub
			case "0100":
			{
				JR=false;
				BNE=false;
				Jump=false;
				PCSrc=false;
				MemToReg=0;
				ALUSrc=false;
				RegWrite=true;
				RegDst=1;
				MemWrite=false;
				MemRead=false;
				ALUOp=2;
				break;
			}
			// nand
			case "0111":
			{
				JR=false;
				BNE=false;
				Jump=false;
				PCSrc=false;
				MemToReg=0;
				ALUSrc=false;
				RegWrite=true;
				RegDst=1;
				MemWrite=false;
				MemRead=false;
				ALUOp=1;
				break;
			}
			
			case "0":
			{
				JR=false;
				BNE=false;
				Jump=false;
				PCSrc=false;
				MemToReg=0;
				ALUSrc=false;
				RegWrite=false;
				RegDst=1;
				MemWrite=false;
				MemRead=false;
				ALUOp=1;
				break;
			}
			default:
			{
				System.out.println("Incorrect OpCode");
			}
		}
	}
	public static void Execution()
	{
		wire6=DECEX[11]<<1;
		wire2=DECEX[8]+wire6;
		ALU();
	}
	public static void ALU ()
	{
		// ALU Code //
		// ADD 000  //
		// NAND 001 //
		// SUB 010  //
		// MUL 011  //
		// DIV 100  //
		// SLL 101  //
		// SLR 110  //
		// BLT 111  //
		switch(DECEX[6])
		{
			case 0:
			{
				aluResult=DECEX[9]+(DECEX[7]==1?DECEX[11]:DECEX[10]);
				if(aluResult==0)
					zero=true;
				else
					zero=false;
				break;
			}
			case 1:
			{
				aluResult=~(DECEX[9] & (DECEX[7]==1?DECEX[11]:DECEX[10]));
				if(aluResult==0)
					zero=true;
				else
					zero=false;
				break;
			}
			case 2:
			{
				aluResult=DECEX[9]-(DECEX[7]==1?DECEX[11]:DECEX[10]);
				if(aluResult==0)
					zero=true;
				else
					zero=false;
				break;
			}
			case 3:
			{
				aluResult=DECEX[9]*(DECEX[7]==1?DECEX[11]:DECEX[10]);
				if(aluResult==0)
					zero=true;
				else
					zero=false;
				break;
			}
			case 4:
			{
				aluResult=(int)(DECEX[9]/(DECEX[7]==1?DECEX[11]:DECEX[10]));
				if(aluResult==0)
					zero=true;
				else
					zero=false;
				break;
			}
			case 5:
			{
				aluResult=DECEX[9]<<(DECEX[7]==1?DECEX[11]:DECEX[10]);
				if(aluResult==0)
					zero=true;
				else
					zero=false;
				break;
			}
			case 6:
			{
				aluResult=DECEX[9]>>(DECEX[7]==1?DECEX[11]:DECEX[10]);
				if(aluResult==0)
					zero=true;
				else
					zero=false;
				break;
			}
			case 7:
			{
				aluResult = DECEX[9]-(DECEX[7]==1?DECEX[11]:DECEX[10]);
				String x = Integer.toBinaryString(aluResult);
				if(x.charAt(0) == '1' && x.length()==32)
					zero=true;
				else
					zero=false;
				break;
			}
			default:
				System.out.println("Incorrect ALUOp");
		}
	}
	//int address, int memWriteData, boolean MemWrite, boolean MemRead
	
	public static void memoryAndLink() {
		if((EXMEM[12]==1 && EXMEM[6]==1) || (EXMEM[13]==1 && !(EXMEM[6]==1))){
			wire3=EXMEM[5];
			flush();
		}
		else
			wire3=PC;
		if(EXMEM[14]==1){
			wire5=EXMEM[11];
			flush();
		}
		else
			wire5=wire3;
		if(EXMEM[15]==1){
			wire5=EXMEM[17]<<1;
			flush();
		}
		PC=wire5;
		dataMemory();
	}
	
	public static void dataMemory ()
	{
		try
		{
			BufferedReader br=new BufferedReader(new FileReader("./src/DataMemory.txt"));
			String data="";
			while(br.ready())
			{
				data=data+br.readLine()+"\n";
			}
			br.close();
			String[] splittedData = data.split("\n");
			if(EXMEM[4]==1)
			{
				if(EXMEM[7] < splittedData.length && !splittedData[EXMEM[7]].equals(""))
					memReadData=Integer.parseInt(splittedData[EXMEM[7]]);
				else
					memReadData=0;
			}
			else if(EXMEM[3]==1)
			{
				String x = "";
				if(EXMEM[7] < splittedData.length-1)
				{
					splittedData[EXMEM[7]]=EXMEM[8]+"";
					for(int i=0;i<splittedData.length;i++)
					{
						x=x+splittedData[i]+"\n";
					}
				}
				else
				{
					for(int i=0;i<EXMEM[7];i++)
					{
						if(i<splittedData.length)
							x=x+splittedData[i]+"\n";
						else
							x=x+"\n";
					}
					x=x+EXMEM[8];
				}
				FileWriter fw=new FileWriter(new File("./src/DataMemory.txt"));
				PrintWriter pw=new PrintWriter(fw);
				pw.print(x);
				pw.close();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	//int writeRegister, int regWriteData, boolean RegWrite
	public static void writeBack ()
	{
		if(MEMWRB[1]==1)
			wire7=MEMWRB[2];
		else if(MEMWRB[1]==0)
			wire7=MEMWRB[3];
		else
			wire7=MEMWRB[5];
		if(MEMWRB[0]==1)
		{
			registers[MEMWRB[4]]=wire7;
		}
	}


	public static void flush(){
		//set Execution attri
				DECEX[0]=0;
				DECEX[2]=0;
				DECEX[3]=0;
				DECEX[4]=0;
				DECEX[5]=0;
				DECEX[7]=0;
				DECEX[14]=0;
				DECEX[15]=0;
				DECEX[16]=0;
				DECEX[17]=0;
		//set Fetch attri
				FETDEC="";

	}

}



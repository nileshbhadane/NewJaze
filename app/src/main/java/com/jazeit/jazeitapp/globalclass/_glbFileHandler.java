package com.jazeit.jazeitapp.globalclass;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.content.Context;
import android.widget.Toast;

public class _glbFileHandler {

	private String _sFileName = null;
	private File sfile;
	private String eol = System.getProperty("line.separator");
	public _glbFileHandler(String sFileName) { this._sFileName = sFileName; }

	public boolean FileExists() { sfile = new File(_sFileName); if(sfile.exists()) { return true; }  else { return false; } }	
	public void DeleteFile(Context _oContext) { sfile= new File(_sFileName); if (sfile.exists()){ if(sfile.isFile()) { sfile.delete(); } } }

	public String ReadAll (Context oContext )
	{
		String bytesRead = null;
		String _ReturnValues = null;
		BufferedReader _BR = null;
		InputStream _IS = null;
		String retVal = "";
		try {
			sfile = new File(_sFileName);
			_IS = new FileInputStream(sfile);
			_BR = new BufferedReader(new InputStreamReader(_IS,"UTF-8"));
			while ((bytesRead = _BR.readLine()) != null) {
				_ReturnValues = new String( bytesRead);
				retVal = retVal + _ReturnValues; 
			}
		} catch(Exception e) { Toast.makeText(oContext, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
		} finally {
			try { _IS.close(); _BR.close(); } catch (IOException e) {
				Toast.makeText(oContext, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
			}
		}
		return retVal;
	}
	public ArrayList<String> ReadLines(Context oContext) {
		String bytesRead = null;
		ArrayList<String> list = new ArrayList<String>(); 
		String _ReturnValues = null;
		BufferedReader _BR = null;
		InputStream _IS = null;
		try {
			sfile = new File(_sFileName);
			_IS = new FileInputStream(sfile);
			_BR = new BufferedReader(new InputStreamReader(_IS,"UTF-8"));
			while ((bytesRead = _BR.readLine()) != null) {
				_ReturnValues = new String( bytesRead);
				list.add(_ReturnValues );
			}
		} catch(Exception e) { Toast.makeText(oContext, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
		} finally {
			try { _IS.close(); _BR.close(); } catch (IOException e) {
				Toast.makeText(oContext, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
			}
		}
		return list;
	}
	
	public void AppendLine (String _lineToWrite, Context oContext){ 
		BufferedWriter _BufferWriter = null;
		try {
			sfile = new File(_sFileName);
			if ( FileExists()== true ) _BufferWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sfile, true), "UTF-8" ));
			else _BufferWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sfile), "UTF-8" ));
			
			_BufferWriter.write(_lineToWrite );
			_BufferWriter.newLine();
			
		} catch(IOException e) {
			Toast.makeText(oContext, e.getMessage().toString(), Toast.LENGTH_SHORT).show();;
		} finally {
			try { _BufferWriter.close();
			} catch (IOException e) {
				Toast.makeText(oContext, e.getMessage().toString(), Toast.LENGTH_SHORT).show();;
			}
		}
	}
	public void WriteLines(ArrayList<String> _LinesToWrite, Context oContext){ 
		BufferedWriter _BufferWriter = null;
		eol = System.getProperty("line.separator");
		try{
			sfile = new File(_sFileName);
			_BufferWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sfile), "UTF-8"));
			for ( String cw:_LinesToWrite){
				_BufferWriter.write(cw  + eol);
			}
		} catch (IOException e) {
			Toast.makeText(oContext, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
		} finally {
			try {
				_BufferWriter.close();
			} catch (IOException e) {
				Toast.makeText(oContext, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
			}
		}
	}
	public void WriteLines(String[] _LinesToWrite, Context oContext){ 
		BufferedWriter _BufferWriter = null;
		eol = System.getProperty("line.separator");
		try{
			sfile = new File(_sFileName);
			_BufferWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sfile), "UTF-8"));
			for ( String cw:_LinesToWrite) { _BufferWriter.write(cw + eol);}
		} catch (IOException e) { Toast.makeText(oContext, e.getMessage().toString(), Toast.LENGTH_SHORT).show(); }
			finally {
			try { _BufferWriter.close();} 
			catch (IOException e) { Toast.makeText(oContext, e.getMessage().toString(), Toast.LENGTH_SHORT).show();}
		}
	}

	public ArrayList <String> ReadLinesById (Context oContext, String SearchValue, int FieldIndex) {
		String lineRead = null;
		String _ReturnValues = null;
		BufferedReader _BR = null;
		InputStream _IS = null;
		ArrayList<String> retVal = new ArrayList<String>();
		try {
			sfile = new File(_sFileName);
			_IS = new FileInputStream(sfile);
			_BR = new BufferedReader(new InputStreamReader(_IS,"UTF-8"));
			while ((lineRead = _BR.readLine()) != null) {
				String[] sValue = lineRead.split("~");
				if(sValue[FieldIndex].compareTo(SearchValue) == 0){
					_ReturnValues = new String ( lineRead );
					retVal.add( _ReturnValues);
				}
			}
		} catch(Exception e) { Toast.makeText(oContext, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
		} finally {
			try { _IS.close(); _BR.close(); } catch (IOException e) {
				Toast.makeText(oContext, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
			}
		}
		return retVal;
	}

	
	public void DeleteLineById ( Context oContext, String SearchStr, int FieldIndex )
	{
		ArrayList <String> Lines = ReadLines(oContext);
		ArrayList <String> LinesAfterDelete = new ArrayList<String>() ;
		String _ReturnValues;
		int LineCount = Lines.size();
		int iCtr ;
		for ( iCtr = 0; iCtr < LineCount; iCtr++)
		{ 
			String subValues[] =  Lines.get(iCtr).split("~");
			if(SearchStr.compareTo (subValues[FieldIndex]) == 0) {} else {
				_ReturnValues = new String ( Lines.get(iCtr) );
				LinesAfterDelete.add(_ReturnValues);
			}
		}
		WriteLines(LinesAfterDelete, oContext);
	}
	
	public void WriteLineById(Context oContext, String SearchStr, int FieldIndex, String WriteValue){
		ArrayList <String> Lines = ReadLines(oContext);
		int LineCount = Lines.size();
		int iCtr ;
		for ( iCtr = 0; iCtr < LineCount; iCtr++)
		{ 
			String subValues[] =  Lines.get(iCtr).split("~");
			if(SearchStr.compareTo(subValues[FieldIndex]) == 0) { Lines.set(iCtr, WriteValue); }
		}
		WriteLines(Lines, oContext);
	}
	
	
	/// TO CHECK and FIX
	public void WriteLineByField(Context oContext, String SearchStr, int FieldIndex, int WriteFieldIndex, String WriteFieldValue){
		ArrayList <String> Lines = ReadLines(oContext);
		int LineCount = Lines.size();
		int iCtr ;
		String newLine = null;  
		for ( iCtr =0; iCtr < LineCount; iCtr++)
		{ 
			String subValues[] =  Lines.get(iCtr).split("~");
			//Id check
			if(SearchStr.compareTo(subValues[FieldIndex]) == 0){
				for(int jCtr = 0; jCtr < subValues.length; jCtr ++){
					subValues[WriteFieldIndex] = subValues[WriteFieldIndex].replace(subValues[WriteFieldIndex],WriteFieldValue);
				}
				newLine = subValues[0] + "~" + subValues[1] + "~" + subValues[2] + "~" + subValues[3] + "~" +subValues[4];
				Lines.set(iCtr, newLine);
			}		
		}
		System.out.println(Lines.toString());
		WriteLines(Lines, oContext);
	}

}

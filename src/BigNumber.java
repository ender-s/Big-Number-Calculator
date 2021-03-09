import java.util.ArrayList;

public class BigNumber 
{

	private ArrayList<Integer> Digits = new ArrayList<>();
	private int Sign = 0;
	
	public BigNumber (String bigNumber)
	{
		
		if (bigNumber.charAt(0) == '-')
		{
			Sign = 1;
			bigNumber = bigNumber.substring(1);
		}

		
		while (bigNumber.charAt(0) == '0' && bigNumber.length() > 1)
		{
			bigNumber = bigNumber.substring(1);
		}

		
		for(int i = 0; i < bigNumber.length(); i++)
		{
			Digits.add(Integer.parseInt(bigNumber.substring(i, i+1)));
		}
		
		
	}
	
	
	public String toString()
	{
		
		String result = "";
		
		if (Sign == 1) 
		{
			result = "-";
		}
		
		for (int i = 0; i < Digits.size(); i++)
		{
			result += Digits.get(i);
		}
		
		return result;
		
	}
	
	public BigNumber Minimum (BigNumber Compared) 
	{
		if (compareTo(Compared) >= 0)
		{
			return Compared;
		}
		
		else 
		{
			return this;
		}
	}
	
	public BigNumber Maximum (BigNumber Compared) 
	{
		if (compareTo(Compared) <= 0) 
		{
			return Compared;
		}
		
		else
		{
			return this;
		}
		
	}

	public BigNumber Add(BigNumber Second)
	{
		BigNumber newBigNumber = null;
		String newDigits = "";
		if (this.Sign == Second.Sign)
		{
			
			BigNumber longer, shorter;
			if (this.Digits.size() >= Second.Digits.size())
			{
				longer = this;
				shorter = Second;
			}
			else 
			{
				longer = Second;
				shorter = this;
			}
			
			
			int carry = 0;
			for (int i = longer.Digits.size()-1, i2 = shorter.Digits.size()-1; i >= 0; i--, i2--)
			{
				int sum = carry + longer.Digits.get(i);;
				if (i2 >= 0) 
				{
					sum += shorter.Digits.get(i2);
				}

				
				if (sum >= 10)
				{
					newDigits = (sum % 10) + newDigits;
					carry = sum / 10;
				} 
				else
				{
					newDigits = sum + newDigits;
					carry = 0;
				}
			}
			
			if (carry != 0)
				newDigits = carry + newDigits;

			
			if (this.Sign == 1)
			{
				
				newDigits = "-" + newDigits;
				
			}
			
			newBigNumber = new BigNumber(newDigits);
		}
		
		else
		{
			
			if (this.Sign == 0)
			{
				Second.setSign(0);
				newBigNumber = this.Subtract(Second);
				Second.setSign(1);
			}
			else
			{
				this.setSign(0);
				newBigNumber = Second.Subtract(this);
				this.setSign(1);
				
			}
			
		}
		
		
		return newBigNumber;
		
		
	}
	
	public BigNumber Subtract(BigNumber Second)
	{
		BigNumber newBigNumber = null;
		String newDigits = "";
		
		if (this.Sign == Second.Sign)
		{
			
			BigNumber max, min;
			
			// taking the number whose unsigned part is larger as max, and the other as min.
			if (this.Sign == 1)
			{
				
				min = this.Maximum(Second);
				max = this.Minimum(Second);
			}
			
			else
			{
				min = this.Minimum(Second);
				max = this.Maximum(Second);
				
			}

			
			int carry = 0;
			for (int i = max.Digits.size()-1, i2 = min.Digits.size()-1; i >= 0; i--, i2--)
			{
				int minuend, subtrahend;
				minuend = max.Digits.get(i);
				if (i2 >= 0)
				{
					subtrahend = min.Digits.get(i2);
				}
				else 
				{
					subtrahend = 0;
				}
				
				
				if (minuend >= carry)
				{
					minuend -= carry;
					carry = 0;
				}
				else
				{
					minuend = (minuend + 10) - carry;
				}
				

				if (minuend >= subtrahend)
				{
					minuend -= subtrahend;
				}
				else
				{
					minuend = (minuend + 10) - subtrahend;
					carry += 1;
				}
				
				newDigits = minuend + newDigits;
			}
			
			while(newDigits.charAt(0) == '0' && newDigits.length() > 1)
			{
				newDigits = newDigits.substring(1);
			}
			
			if (compareTo(Second) == -1)
			{
				newDigits = "-" + newDigits;
			}
			
			if (compareTo(Second) == 0)
			{
				newDigits = "0";
			}
			
			newBigNumber = new BigNumber(newDigits);

		}
		else
		{
			if (this.Sign == 0)
			{
				Second.setSign(0);
				newBigNumber = this.Add(Second);
				Second.setSign(1);	
			}
			
			else 
			{
				Second.setSign(1);
				newBigNumber = this.Add(Second);
				Second.setSign(0);
			}
			
			
		}
		
		return newBigNumber;
		
	}
	
	public BigNumber Mul(BigNumber Second)
	{
		
		int signOfResult = 0;
		final BigNumber ZERO = new BigNumber("0");
		
		if (this.compareTo(ZERO) == 0 || Second.compareTo(ZERO) == 0)
		{
			return ZERO;
		}
		
		if (this.Sign != Second.getSign()) 
		{
			
			signOfResult = 1;
			
		}
		
		ArrayList<BigNumber> intermediateResults = new ArrayList<BigNumber>();
		
		for (int i = Second.Digits.size()-1; i >= 0; i--) 
		{
		
			String intermediateResult = "";
			int carry = 0;
			for (int j = this.Digits.size()-1; j >=0; j--)
			{
				
				int productOfDigits = Second.getDigits().get(i)*this.getDigits().get(j);
				productOfDigits += carry;
				carry = productOfDigits / 10;
				
				if (j == 0)
				{
					intermediateResult = productOfDigits + intermediateResult;
				}
				
				else 
				{
					intermediateResult = (productOfDigits % 10) + intermediateResult;
				}
			}
			for (int x = 0; x < Second.Digits.size()-i-1; x++)
			{
				intermediateResult += "0";
			}
			
			intermediateResults.add(new BigNumber(intermediateResult));
			
		}

		
		BigNumber sum = intermediateResults.get(0);
		for (int i = 1; i < intermediateResults.size(); i++) 
		{
			sum = sum.Add(intermediateResults.get(i));
		}
		
		sum.setSign(signOfResult);
		return sum;
		
		
		
	}
	
	public BigNumber Mod(BigNumber Second)
	{
		
		return Div(Second)[1];
		
	}
	
	public BigNumber[] Div(BigNumber Second)
	{
		
		int signOfResult = 0;
		if (this.Sign != Second.getSign())
		{
			signOfResult = 1;
		}
		
		int signOfThis = this.Sign;
		int signOfSecond = Second.getSign();
		
		this.setSign(0);
		Second.setSign(0);
		
		if (this.compareTo(Second) < 0) 
		{	
			BigNumber mod;
			if (signOfResult == 1) 
			{
				mod = Second.Subtract(this);
				mod.setSign(signOfSecond);

			}
			else
			{
				mod = new BigNumber(this.toString());
				mod.setSign(signOfThis);

			}
			
			BigNumber[] res = {new BigNumber("0"), mod};
			return res;
		}
		
		
		
		String unProcessedPart = this.toString();
		String result = "";

		String reducedDigits = "";
		BigNumber reducedNumber = null;
		
		for (int i = 0; i < unProcessedPart.length(); i++)
		{
			reducedDigits += unProcessedPart.charAt(i);
			reducedNumber = new BigNumber(reducedDigits);
			
			BigNumber digit = new BigNumber(String.valueOf(calculateDivisionDigit(reducedNumber, Second)));
			reducedNumber = reducedNumber.Subtract(digit.Mul(Second));
			result += digit.toString();
			reducedDigits = reducedNumber.toString();
		}
		
		while (result.charAt(0) == '0' && result.length() > 1)
		{
			result = result.substring(1);
		}
		
		
		
		BigNumber toBeReturned = new BigNumber(result);
		toBeReturned.setSign(signOfResult);
		BigNumber remainder; 
		
		if (signOfResult == 1)
		{
			remainder = Second.Subtract(new BigNumber(reducedDigits));
			if (signOfSecond == 1)
			{
				remainder.setSign(1);
			}
		}
		
		else
		{
			remainder = new BigNumber(reducedDigits);
			remainder.setSign(signOfThis);
		}
		
		BigNumber[] lastResult = {toBeReturned, remainder};
		
		this.setSign(signOfThis);
		Second.setSign(signOfSecond);
		
		return lastResult;
		
	}
	
	public int calculateDivisionDigit(BigNumber a, BigNumber b)
	{
		
		BigNumber digit = new BigNumber("9");

		while (a.compareTo(b.Mul(digit)) < 0)
		{
			digit = digit.Subtract(new BigNumber("1"));
		}
		
		if (digit.getDigits().size() == 0)
		    return 0;

		return digit.getDigits().get(0);
		
	}
	
	public int compareTo(BigNumber Second)
	{
		//returns 1 when this > second. Returns -1 when this < second. Returns 0 when this = second
		int result = 0;
		if (this.Sign != Second.Sign)
		{
			//signs of these two numbers are different.
			if (this.Sign == 0) 
			{
				//this number is positive, Second is negative. So this > Second
				result = 1;
				
			}
			
			else
			{
				//this number is negative, Second is positive. So Second > this
				result = -1;
			}
		}
		
		else
		{
			//these two numbers have the same sign.
			if (this.Digits.size() > Second.Digits.size())
			{
				result = 1;
			}
			else if (this.Digits.size() < Second.Digits.size())
			{
				result = -1;
			}
			else 
			{
				for (int i = 0; i < this.Digits.size(); i++)
				{
					
					if (this.Digits.get(i) > Second.Digits.get(i))
					{
						result = 1;
						break;
					}
					
					if (this.Digits.get(i) < Second.Digits.get(i))
					{
						result = -1;
						break;
					}
					
				}
			}
			
			if (this.Sign == 1)
			{
				// if the sign is negative, then the comparison result must be reversed.

				if (result == 1)
				{
					result = -1;
				}
				
				else if (result == -1)
				{
					result = 1;
				}
			}
			
		}
		
		return result;
	}

	
	//** GETTERS **//
	public int getSign()
	{
		return Sign;
	}
	
	public ArrayList<Integer> getDigits()
	{
		return Digits;
	}
	
	//** SETTERS **//
	public void setSign(int Sign)
	{
		this.Sign = Sign;
	}
	
	public void setDigits(ArrayList<Integer> Digits)
	{
		this.Digits = Digits;
	}
	
}

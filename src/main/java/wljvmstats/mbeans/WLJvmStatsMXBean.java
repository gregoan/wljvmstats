//Copyright (C) 2011-2013 Paul Done . All rights reserved.
//This file is part of the HostMachineStats software distribution. Refer to 
//the file LICENSE in the root of the HostMachineStats distribution.
//THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
//AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
//IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
//ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE 
//LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
//CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
//SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
//INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
//CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
//ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
//POSSIBILITY OF SUCH DAMAGE.
package wljvmstats.mbeans;

/**
 * MBean exposing statistics for the JVM running this WebLogic
 * Server instances. Provides read-only attributes for useful JVM usages statistics.
 *  
 * @see javax.management.MXBean
 */
public interface WLJvmStatsMXBean {
	
	public double getHeapMemoryInit();
	public double getHeapMemoryUsed();
	public double getHeapMemoryCommitted();
	public double getHeapMemoryMax();
    
	public double getNonHeapMemoryInit();
	public double getNonHeapMemoryUsed();
	public double getNonHeapMemoryCommitted();
	public double getNonHeapMemoryMax();
	
	public double getEdenSpaceInit();
	public double getEdenSpaceUsed();
	public double getEdenSpaceCommitted();
	public double getEdenSpaceMax();
	
	public double getSuvivorSpaceInit();
	public double getSurvivorSpaceUsed();
	public double getSurvivorSpaceCommitted();
	public double getSurvivorSpaceMax();
	
	public double getTenuredGenInit();
	public double getTenuredGenUsed();
	public double getTenuredGenCommitted();
	public double getTenuredGenMax();
	
	public double getPermGenInit();
	public double getPermGenUsed();
	public double getPermGenCommitted();
	public double getPermGenMax();
	
	public double getMetaSpaceInit();
	public double getMetaSpaceUsed();
	public double getMetaSpaceCommitted();
	public double getMetaSpaceMax();
}
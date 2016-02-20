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

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.util.List;

import javax.management.MBeanRegistration;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import weblogic.logging.NonCatalogLogger;

/**
 * Implementation of the MBean exposing O.S/machine statistics for the machine
 * hosting this WebLogic Server instances. Provides read-only attributes for 
 * useful CPU, Memory and Network related usages statistics.Use SIGAR JNI/C 
 * libraries under the covers (http://support.hyperic.com/display/SIGAR/Home) 
 * to retrieve specific statistics from host operating system.
 *  
 * @see javax.management.MXBean
 */
public class WLJvmStats implements WLJvmStatsMXBean, MBeanRegistration {
	/**
	 * Main constructor
	 * 
	 * @param netInterfaceNames Comma separated list of names of the preferred network interface to try to monitor
	 */
	public WLJvmStats() {
		log = new NonCatalogLogger(WLJVMS_APP_NAME);
	}
	
	/**
	 * Pre-register event handler - returns MBean name.
	 * 
	 * @return name
	 */
	public ObjectName preRegister(MBeanServer server, ObjectName name) throws Exception {
		return name;
	}

	/**
	 * Post-register event handler - logs that started.
	 * 
	 * @param registrationDone Indicates if registration was completed
	 */
	public void postRegister(Boolean registrationDone) {
		log.notice("WlJvmStats MBean initialised");
	}

	/**
	 * Pre-deregister event handler - does nothing
	 * 
	 * @throws Exception Indicates problem is post registration
	 */
	public void preDeregister() throws Exception {
	}

	/**
	 * Post-deregister event handler - logs that stopped
	 */
	public void postDeregister() {
		log.notice("WlJvmStats MBean destroyed");
	}

	/**
	 * The version of the WLHostMachineStats MBean. 
	 * Format: "x.x.x". Example: "0.1.0".
	 * 
	 * @return The version of WLHostMachineStats MBean
	 */
	public String getMBeanVersion() {
		return WLJVMS_APP_VERSION;
	}
	
	/**
	 * 
	 */
	public double getHeapMemoryInit() {
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
	    return memoryMXBean.getHeapMemoryUsage().getInit() / BYTES_PER_MEGABYTE;
	}
	
	/**
	 * 
	 */
	public double getHeapMemoryUsed() {
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
	    return memoryMXBean.getHeapMemoryUsage().getUsed() / BYTES_PER_MEGABYTE;
	}
	
	/**
	 * 
	 */
	public double getHeapMemoryCommitted() {
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
	    return memoryMXBean.getHeapMemoryUsage().getCommitted() / BYTES_PER_MEGABYTE;
	}
	
	/**
	 * 
	 */
	public double getHeapMemoryMax() {
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
	    return memoryMXBean.getHeapMemoryUsage().getMax() / BYTES_PER_MEGABYTE;
	}
    
	/**
	 * 
	 */
	public double getNonHeapMemoryInit() {
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
	    return memoryMXBean.getNonHeapMemoryUsage().getInit() / BYTES_PER_MEGABYTE;
	}
	
	/**
	 * 
	 */
	public double getNonHeapMemoryUsed() {
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
	    return memoryMXBean.getNonHeapMemoryUsage().getUsed() / BYTES_PER_MEGABYTE;
	}
	
	/**
	 * 
	 */
	public double getNonHeapMemoryCommitted() {
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
	    return memoryMXBean.getNonHeapMemoryUsage().getCommitted() / BYTES_PER_MEGABYTE;
	}
	
	/**
	 * 
	 */
	public double getNonHeapMemoryMax() {
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
	    return memoryMXBean.getNonHeapMemoryUsage().getMax() / BYTES_PER_MEGABYTE;	
	}
	
	/**
	 * 
	 */
	public double getEdenSpaceInit() {

//System.out.println("getEdenSpaceInit");
		
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {        	
        	if(EDEN_SPACE.equals(memoryPoolMXBean.getName())) {
        		
//System.out.println("getEdenSpaceInit - Return [" + memoryPoolMXBean.getUsage().getInit() / BYTES_PER_MEGABYTE + "]");
        		
        		return memoryPoolMXBean.getUsage().getInit() / BYTES_PER_MEGABYTE;
        	}
        }
//System.out.println("getEdenSpaceInit - Ouppssss something weird happened ...");
        return 0;
	}
	
	/**
	 * 
	 */
	public double getEdenSpaceUsed() {
		
//System.out.println("getEdenSpaceUsed");
		
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {
        	if(EDEN_SPACE.equals(memoryPoolMXBean.getName())) {
        		return memoryPoolMXBean.getUsage().getUsed() / BYTES_PER_MEGABYTE;
        	}
        }
//System.out.println("getEdenSpaceUsed - Ouppssss something weird happened ...");
        return 0;
	}
	
	/**
	 * 
	 */
	public double getEdenSpaceCommitted() {
	
//System.out.println("getEdenSpaceCommitted");
		
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {
        	if(EDEN_SPACE.equals(memoryPoolMXBean.getName())) {
        		return memoryPoolMXBean.getUsage().getCommitted() / BYTES_PER_MEGABYTE;
        	}
        }
//System.out.println("getEdenSpaceCommitted - Ouppssss something weird happened ...");
        return 0;
	}
	
	/**
	 * 
	 */
	public double getEdenSpaceMax() {
		
//System.out.println("getEdenSpaceMax");
		
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {
        	if(EDEN_SPACE.equals(memoryPoolMXBean.getName())) {
        		return memoryPoolMXBean.getUsage().getMax() / BYTES_PER_MEGABYTE;
        	}
        }
//System.out.println("getEdenSpaceMax - Ouppssss something weird happened ...");
        return 0;
	}
	
	/**
	 * 
	 */
	public double getSuvivorSpaceInit() {
	
//System.out.println("getSurvivorSpaceInit");

		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {
        	if(SURVIVOR_SPACE.equals(memoryPoolMXBean.getName())) {
	            return memoryPoolMXBean.getUsage().getInit()/ BYTES_PER_MEGABYTE;
        	}
        }
//System.out.println("getSurvivorSpaceInit - Ouppssss something weird happened ...");
        return 0;
	}
	
	/**
	 * 
	 */
	public double getSurvivorSpaceUsed() {

//System.out.println("getSurvivorSpaceUsed");
		
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {
        	if(SURVIVOR_SPACE.equals(memoryPoolMXBean.getName())) {
        		return memoryPoolMXBean.getUsage().getUsed() / BYTES_PER_MEGABYTE;
        	}
        }
//System.out.println("getSurvivorSpaceUsed - Ouppssss something weird happened ...");
        return 0;
	}
	
	/**
	 * 
	 */
	public double getSurvivorSpaceCommitted() {
	
//System.out.println("getSurvivorSpaceCommitted");
		
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {
        	if(SURVIVOR_SPACE.equals(memoryPoolMXBean.getName())) {
	            return memoryPoolMXBean.getUsage().getCommitted() / BYTES_PER_MEGABYTE;
        	}
        }
//System.out.println("getSurvivorSpaceICommitted - Ouppssss something weird happened ...");
        return 0;
	}
	
	/**
	 * 
	 */
	public double getSurvivorSpaceMax() {
		
//System.out.println("getSurvivorSpaceMax");
		
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {
        	if(SURVIVOR_SPACE.equals(memoryPoolMXBean.getName())) {
	            return memoryPoolMXBean.getUsage().getMax() / BYTES_PER_MEGABYTE;
        	}
        }
//System.out.println("getSurvivorSpaceMax - Ouppssss something weird happened ...");
        return 0;
	}
	
	/**
	 * 
	 */
	public double getTenuredGenInit() {
		
//System.out.println("getTenuredGenInit");
		
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {        	
        	if(TENURED_GEN.equals(memoryPoolMXBean.getName())) {
	            return memoryPoolMXBean.getUsage().getInit()/ BYTES_PER_MEGABYTE;
        	}
        }
//System.out.println("getTenuredInit - Ouppssss something weird happened ...");
        return 0;
	}
	
	/**
	 * 
	 */
	public double getTenuredGenUsed() {
		
//System.out.println("getTenuredGenUsed");
		
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {
        	if(TENURED_GEN.equals(memoryPoolMXBean.getName())) {
	            return memoryPoolMXBean.getUsage().getUsed() / BYTES_PER_MEGABYTE;
        	}
        }
//System.out.println("getTenuredGenUsed - Ouppssss something weird happened ...");
        return 0;
	}
	
	/**
	 * 
	 */
	public double getTenuredGenCommitted() {
		
//System.out.println("getTenuredGenCommitted");
		
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {        	
        	if(TENURED_GEN.equals(memoryPoolMXBean.getName())) {
	            return memoryPoolMXBean.getUsage().getCommitted() / BYTES_PER_MEGABYTE;
        	}
        }
//System.out.println("getTenuredGenCommitted - Ouppssss something weird happened ...");
        return 0;
	}
	
	/**
	 * 
	 */
	public double getTenuredGenMax() {
		
//System.out.println("getTenuredGenMax");
		
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {
           	if(TENURED_GEN.equals(memoryPoolMXBean.getName())) {
	            return memoryPoolMXBean.getUsage().getMax() / BYTES_PER_MEGABYTE;
        	}
        }
//System.out.println("getTenuredGenMax - Ouppssss something weird happened ...");
        return 0;
	}
	
	/**
	 * 
	 */
	public double getPermGenInit() {
	
//System.out.println("getPermGenInit");
		
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {        	
        	if(PERM_GEN.equals(memoryPoolMXBean.getName())) {
	            return memoryPoolMXBean.getUsage().getInit()/ BYTES_PER_MEGABYTE;
        	}
        }
//System.out.println("getPermGenInit - Ouppssss something weird happened ...");
        return 0;
	}
	
	/**
	 * 
	 */
	public double getPermGenUsed() {
		
//System.out.println("getPermGenUsed");
		
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {        	
        	if(PERM_GEN.equals(memoryPoolMXBean.getName())) {
	            return memoryPoolMXBean.getUsage().getUsed() / BYTES_PER_MEGABYTE;
        	}
        }
//System.out.println("getPermGenUsed - Ouppssss something weird happened ...");
        return 0;
	}
	
	/**
	 * 
	 */
	public double getPermGenCommitted() {
		
//System.out.println("getPermGenCommitted");
		
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {        	
        	if(PERM_GEN.equals(memoryPoolMXBean.getName())) {        		
	            return memoryPoolMXBean.getUsage().getCommitted() / BYTES_PER_MEGABYTE;
        	}
        }
//System.out.println("getPermGenCommitted - Ouppssss something weird happened ...");
        return 0;
	}
	
	/**
	 * 
	 */
	public double getPermGenMax() {
		
//System.out.println("getPermGenMax");
		
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {        	
        	if(PERM_GEN.equals(memoryPoolMXBean.getName())) {
	            return memoryPoolMXBean.getUsage().getMax() / BYTES_PER_MEGABYTE;
        	}
        }
//System.out.println("getPermGenMax - Ouppssss something weird happened ...");
        return 0;
	}
	
	// Constants
	private static final String WLJVMS_APP_NAME = "WLJvmStats";
	private static final String WLJVMS_APP_VERSION = "0.0.1";
	//private static final int PERCENT = 100;
	private static final int BYTES_PER_MEGABYTE = 1024*1024;
	
	private static final String EDEN_SPACE = "Eden Space";
	private static final String SURVIVOR_SPACE = "Survivor Space";
	private static final String TENURED_GEN = "Tenured Gen";
	private static final String PERM_GEN = "Perm Gen";
	
	// Members 
	private final NonCatalogLogger log;
}

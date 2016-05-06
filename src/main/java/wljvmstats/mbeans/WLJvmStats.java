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
	
	// Constants
	private static final String WL_JVM_APP_NAME = "WLJvmStats";
	private static final String WL_JVM_APP_VERSION = "0.0.1";
	//private static final int PERCENT = 100;
	private static final int BYTES_PER_MEGABYTE = 1024*1024;
	
	private static final String EDEN_SPACE = "Eden Space";
	private static final String SURVIVOR_SPACE = "Survivor Space";
	
	private static final String OLD_GEN = "Old Gen";
	private static final String TENURED_GEN = "Tenured Gen";
	
	private static final String PERM_GEN = "Perm Gen";
	
	// Members 
	private final NonCatalogLogger log;
		
	/**
	 * Main constructor
	 * 
	 * @param netInterfaceNames Comma separated list of names of the preferred network interface to try to monitor
	 */
	public WLJvmStats() {
		log = new NonCatalogLogger(WL_JVM_APP_NAME);
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
	 * The version of the WLJvmStats MBean. 
	 * Format: "x.x.x". Example: "0.1.0".
	 * 
	 * @return The version of WLJvmStats MBean
	 */
	public String getMBeanVersion() {
		return WL_JVM_APP_VERSION;
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
		
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {        	
        	if(memoryPoolMXBean.getName().contains(EDEN_SPACE)) {        		
        		return memoryPoolMXBean.getUsage().getInit() / BYTES_PER_MEGABYTE;
        	}
        }
        return -1;
	}
	
	/**
	 * 
	 */
	public double getEdenSpaceUsed() {
				
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {
        	if(memoryPoolMXBean.getName().contains(EDEN_SPACE)) {
        		return memoryPoolMXBean.getUsage().getUsed() / BYTES_PER_MEGABYTE;
        	}
        }
        return -1;
	}
	
	/**
	 * 
	 */
	public double getEdenSpaceCommitted() {
			
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {
        	if(memoryPoolMXBean.getName().contains(EDEN_SPACE)) {
        		return memoryPoolMXBean.getUsage().getCommitted() / BYTES_PER_MEGABYTE;
        	}
        }
        return -1;
	}
	
	/**
	 * 
	 */
	public double getEdenSpaceMax() {
				
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {
        	if(memoryPoolMXBean.getName().contains(EDEN_SPACE)) {
        		return memoryPoolMXBean.getUsage().getMax() / BYTES_PER_MEGABYTE;
        	}
        }
        return -1;
	}
	
	/**
	 * 
	 */
	public double getSuvivorSpaceInit() {
	
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {
        	if(memoryPoolMXBean.getName().contains(SURVIVOR_SPACE)) {
	            return memoryPoolMXBean.getUsage().getInit()/ BYTES_PER_MEGABYTE;
        	}
        }
        return -1;
	}
	
	/**
	 * 
	 */
	public double getSurvivorSpaceUsed() {
		
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {
        	if(memoryPoolMXBean.getName().contains(SURVIVOR_SPACE)) {
        		return memoryPoolMXBean.getUsage().getUsed() / BYTES_PER_MEGABYTE;
        	}
        }
        return -1;
	}
	
	/**
	 * 
	 */
	public double getSurvivorSpaceCommitted() {
			
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {
        	if(memoryPoolMXBean.getName().contains(SURVIVOR_SPACE)) {
	            return memoryPoolMXBean.getUsage().getCommitted() / BYTES_PER_MEGABYTE;
        	}
        }
        return -1;
	}
	
	/**
	 * 
	 */
	public double getSurvivorSpaceMax() {
				
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {
        	if(memoryPoolMXBean.getName().contains(SURVIVOR_SPACE)) {
	            return memoryPoolMXBean.getUsage().getMax() / BYTES_PER_MEGABYTE;
        	}
        }
        return -1;
	}
	
	/**
	 * 
	 */
	public double getTenuredGenInit() {
				
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {        	
        	if(memoryPoolMXBean.getName().contains(TENURED_GEN) || memoryPoolMXBean.getName().contains(OLD_GEN)) {
	            return memoryPoolMXBean.getUsage().getInit()/ BYTES_PER_MEGABYTE;
        	}
        }
        return -1;
	}
	
	/**
	 * 
	 */
	public double getTenuredGenUsed() {
				
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {
        	if(memoryPoolMXBean.getName().contains(TENURED_GEN) || memoryPoolMXBean.getName().contains(OLD_GEN)) {
	            return memoryPoolMXBean.getUsage().getUsed() / BYTES_PER_MEGABYTE;
        	}
        }
        return -1;
	}
	
	/**
	 * 
	 */
	public double getTenuredGenCommitted() {
				
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {        	
        	if(memoryPoolMXBean.getName().contains(TENURED_GEN) || memoryPoolMXBean.getName().contains(OLD_GEN)) {
	            return memoryPoolMXBean.getUsage().getCommitted() / BYTES_PER_MEGABYTE;
        	}
        }
        return -1;
	}
	
	/**
	 * 
	 */
	public double getTenuredGenMax() {
				
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {
        	if(memoryPoolMXBean.getName().contains(TENURED_GEN) || memoryPoolMXBean.getName().contains(OLD_GEN)) {
	            return memoryPoolMXBean.getUsage().getMax() / BYTES_PER_MEGABYTE;
        	}
        }
        return -1;
	}
	
	/**
	 * 
	 */
	public double getPermGenInit() {
			
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {        	
        	if(memoryPoolMXBean.getName().contains(PERM_GEN)) {
	            return memoryPoolMXBean.getUsage().getInit()/ BYTES_PER_MEGABYTE;
        	}
        }
        return -1;
	}
	
	/**
	 * 
	 */
	public double getPermGenUsed() {
				
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {        	
        	if(memoryPoolMXBean.getName().contains(PERM_GEN)) {
	            return memoryPoolMXBean.getUsage().getUsed() / BYTES_PER_MEGABYTE;
        	}
        }
        return -1;
	}
	
	/**
	 * 
	 */
	public double getPermGenCommitted() {
				
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {        	
        	if(memoryPoolMXBean.getName().contains(PERM_GEN)) {
	            return memoryPoolMXBean.getUsage().getCommitted() / BYTES_PER_MEGABYTE;
        	}
        }
        return -1;
	}
	
	/**
	 * 
	 */
	public double getPermGenMax() {
				
		List<MemoryPoolMXBean> list = (List<MemoryPoolMXBean>) ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean memoryPoolMXBean: list)
        {        	
        	if(memoryPoolMXBean.getName().contains(PERM_GEN)) {
	            return memoryPoolMXBean.getUsage().getMax() / BYTES_PER_MEGABYTE;
        	}
        }
        return -1;
	}
}
package com.cooksys.ftd.assignments.collections;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchical;
import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {
	//Private Fields
	private Map<Capitalist, FatCat> workers = new HashMap<Capitalist, FatCat>();
	private Map<FatCat, Set<Capitalist>> owners = new HashMap<FatCat,Set<Capitalist>>(); //parents
	
	//Defensive Copies
	private Map<FatCat, Set<Capitalist>> mapCopy;
	private List<FatCat> listCopy;
	private Set<FatCat> fatSetCopy;
	private Set<Capitalist> capSetCopy;
	
    /**
     * Adds a given element to the hierarchy.
     * <p>
     * If the given element is already present in the hierarchy,
     * do not add it and return false
     * <p>
     * If the given element has a parent and the parent is not part of the hierarchy,
     * add the parent and then add the given element
     * <p>
     * If the given element has no parent but is a Parent itself,
     * add it to the hierarchy
     * <p>
     * If the given element has no parent and is not a Parent itself,
     * do not add it and return false
     *
     * @param capitalist the element to add to the hierarchy
     * @return true if the element was added successfully, false otherwise
     */
    @Override
    public boolean add(Capitalist capitalist) {
    	Capitalist c = capitalist;
    	if(c == null) {
        	//System.out.println("  Failed: Capitalist is null");System.out.flush();
    		return false;
    	}
    	//System.out.println("Adding: " + c.getName());System.out.flush();
    	
        //Is the element already in the hierarchy
        if(this.workers.containsKey(c)) {
        	//System.out.println("  Failed: Already in the hierarchy");System.out.flush();
        	return false;
        } else { //if element is not in the hierarchy
        	//System.out.println("  Not in the hierarchy");System.out.flush();
    		if(c.getClass() == FatCat.class) {
    			//add to hierarchy
    			this.workers.put(c, null);
    			this.owners.put((FatCat) c, new HashSet<Capitalist>());
            	//System.out.println("  A Fat Cat so added whether parentless or not");
    		}
    		
        	//If element has a parent
        	if(c.hasParent()) {
            	//System.out.println("  Has Parent");System.out.flush();
        		//Loop for all parents
        		while(c.getParent() != null) {
	        		//If element's parent is not part of hierarchy (aka parents have not existed before);
	        		if(!this.workers.containsKey(c.getParent())) {
	                	//System.out.println("  Parent not in hierarchy: " + c.getParent().getName() + " : " + c.getParent().getSalary());System.out.flush();
	        			//Add parent
	        			this.workers.put(c.getParent(), null);
	        			this.owners.put(c.getParent(), new HashSet<Capitalist>());
	        			this.owners.get(c.getParent()).add(c);
	        		} else { //if parent is part of hierarchy
	                	//System.out.println("  Parent in hierarchy: " + c.getParent().getName());System.out.flush();
		        		//Check if the set is null
		        		if(this.owners.get(c.getParent()) == null) {
		            		Set<Capitalist> s = this.owners.get(c.getParent());
		            		s = new HashSet<Capitalist>();
		            		this.owners.put(c.getParent(), s);
		        		}
	            		this.owners.get(c.getParent()).add(c);
	        		}
	        		this.workers.put(c, c.getParent());
	        		c = c.getParent();
        		}
            	//System.out.println("  Test: c: " + c.getName() + " cp: " + c.getParent());System.out.flush();
        	} else { //if element does not have a parent
                //System.out.println("  Failed: Parentless WageSlave");System.out.flush();

        		return (c.getClass() == FatCat.class);
        	}
        }

    	//System.out.println("  Added");System.out.flush();
        return true;
    }

    /**
     * @param capitalist the element to search for
     * @return true if the element has been added to the hierarchy, false otherwise
     */
    @Override
    public boolean has(Capitalist capitalist) {
        return this.workers.containsKey(capitalist);
    }

    /**
     * @return all elements in the hierarchy,
     * or an empty set if no elements have been added to the hierarchy
     */
    @Override
    public Set<Capitalist> getElements() {
        return (new HashSet<Capitalist>(workers.keySet()));
    }

    /**
     * @return all parent elements in the hierarchy,
     * or an empty set if no parents have been added to the hierarchy
     */
    @Override
    public Set<FatCat> getParents() {
    	Set<FatCat> parents = owners.keySet();
    	if(parents == null) {
    		return (new HashSet<FatCat>());
    	}
        return (new HashSet<FatCat>(parents));
    }

    /**
     * @param fatCat the parent whose children need to be returned
     * @return all elements in the hierarchy that have the given parent as a direct parent,
     * or an empty set if the parent is not present in the hierarchy or if there are no children
     * for the given parent
     */
    @Override
    public Set<Capitalist> getChildren(FatCat fatCat) {
    	Set<Capitalist> children = this.owners.get(fatCat);
    	if(children == null) {
    		return (new HashSet<Capitalist>());
    	}
    	
    	return (new HashSet<Capitalist>(children));
    }

    /**
     * @return a map in which the keys represent the parent elements in the hierarchy,
     * and the each value is a set of the direct children of the associate parent, or an
     * empty map if the hierarchy is empty.
     */
    @Override
    public Map<FatCat, Set<Capitalist>> getHierarchy() {
		Map<FatCat,Set<Capitalist>> copy = new HashMap<FatCat, Set<Capitalist>>();
    	
    	if(this.owners == null) {
    		return copy;
    	}
    	
    	for (Map.Entry<FatCat, Set<Capitalist>> entry : this.owners.entrySet()) {
    		copy.put(entry.getKey(), new HashSet<Capitalist>(entry.getValue()));
    	}
    	
        return copy;
    }

    /**
     * @param capitalist
     * @return the parent chain of the given element, starting with its direct parent,
     * then its parent's parent, etc, or an empty list if the given element has no parent
     * or if its parent is not in the hierarchy
     */
    @Override
    public List<FatCat> getParentChain(Capitalist capitalist) {
    	List<FatCat> chain = new ArrayList<FatCat>();
    	if(workers.containsKey(capitalist)) {
    		FatCat f = workers.get(capitalist);
    		while(f != null) {
    			chain.add(f);
    			f = f.getParent();
    		}
    	}
    	return chain;
    }
}

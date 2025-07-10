package com.utp.gp.inventarioSMP.util.paginacion;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class PageRender <T>{
    
    private String url;
    private Page<T> page;
    private int totalPaginas;
    private int numElementosPorPagina;
    private int paginaActual;
    private List<PageItem> paginas;

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;        
        this.paginas = new ArrayList<PageItem>();
        
        numElementosPorPagina = 5;
        totalPaginas = page.getTotalPages();
        paginaActual = page.getNumber() + 1;
        
        int desde, hasta;
        if(totalPaginas <= numElementosPorPagina){
            desde = 1;
            hasta = totalPaginas;
        }else{
            if(paginaActual <= numElementosPorPagina/2){
                desde = 1;
                hasta = numElementosPorPagina;
            }
            else if(paginaActual >= totalPaginas - numElementosPorPagina/2){
                desde = totalPaginas - numElementosPorPagina + 1;
                hasta = numElementosPorPagina;
            }
            else{
                desde = paginaActual - numElementosPorPagina/2;
                hasta = numElementosPorPagina;        
            }
        }
        
        for(int i = 0; i < hasta; i++){
            paginas.add(new PageItem(desde + i, paginaActual == desde + i));
        }
    }
    
    public boolean isLast(){
        return page.isLast();
    }
    
    public boolean isFirst(){
        return page.isFirst();
    }
    
    public boolean isHasNext(){
        return page.hasNext();
    }
    
    public boolean isHasPrevius(){
        return page.hasPrevious();
    }
    
}

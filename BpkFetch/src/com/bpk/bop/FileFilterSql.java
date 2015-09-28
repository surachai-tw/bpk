package com.bpk.bop;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author Surachai Torwong
 */
public class FileFilterSql implements FileFilter
{

    String description;
    String extensions[];

    public FileFilterSql(String description, String extension)
    {
        this(description, new String[]
                {
                    extension
                });
    }

    public FileFilterSql(String description, String extensions[])
    {
        if(description == null)
        {
            this.description = extensions[0];
        }
        else
        {
            this.description = description;
        }
        this.extensions = (String[]) extensions.clone();
        toLower(this.extensions);
    }

    private void toLower(String array[])
    {
        for(int i = 0, n = array.length; i < n; i++)
        {
            array[i] = array[i].toLowerCase();
        }
    }

    public String getDescription()
    {
        return description;
    }

    public boolean accept(File file)
    {
        if(file.isDirectory())
        {
            return true;
        }
        else
        {
            String path = file.getAbsolutePath().toLowerCase();
            for(int i = 0, n = extensions.length; i < n; i++)
            {
                String extension = extensions[i];
                if((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.'))
                {
                    return true;
                }
            }
        }
        return false;
    }
}

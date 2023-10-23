package org.launchcode.techjobsmvc.controllers;

import org.launchcode.techjobsmvc.models.Job;
import org.launchcode.techjobsmvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

import static org.launchcode.techjobsmvc.controllers.ListController.columnChoices;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @GetMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.
    @PostMapping("results")
    public String displaySearchResults(Model model, @RequestParam(value="", required = false) String searchType, @RequestParam(value="", required = false) String searchTerm)
    {
        //displays all jobs that match the search requirements
        //makes sure that even if the user enters nothing, it will still display some results

        //create new array to hold all the jobs that match the search criteria
        ArrayList<Job> jobs;

        //if the user searched all, entered nothing, or put a blank line the output will display all the jobs
        if(searchTerm.toUpperCase() == "ALL" || searchTerm.isBlank() || searchTerm.isEmpty())
        {
            jobs = JobData.findAll();
        }
        else
        //find the job that matches the entered criteria
        {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);
        }
        //add all the searched criteria
        model.addAttribute("title", "Jobs with " + columnChoices.get(searchType) + ": " + searchTerm);
        model.addAttribute("searchType", columnChoices);
        model.addAttribute("jobs", jobs);
        return "search";
    }
}


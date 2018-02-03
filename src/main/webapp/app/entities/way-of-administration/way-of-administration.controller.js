(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('Way_of_AdministrationController', Way_of_AdministrationController);

    Way_of_AdministrationController.$inject = ['Way_of_Administration', 'Way_of_AdministrationSearch'];

    function Way_of_AdministrationController(Way_of_Administration, Way_of_AdministrationSearch) {

        var vm = this;

        vm.way_of_Administrations = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Way_of_Administration.query(function(result) {
                vm.way_of_Administrations = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            Way_of_AdministrationSearch.query({query: vm.searchQuery}, function(result) {
                vm.way_of_Administrations = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();

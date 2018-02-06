(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('TimersController', TimersController);

    TimersController.$inject = ['Timers', 'TimersSearch'];

    function TimersController(Timers, TimersSearch) {

        var vm = this;

        vm.timers = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Timers.query(function(result) {
                vm.timers = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            TimersSearch.query({query: vm.searchQuery}, function(result) {
                vm.timers = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();

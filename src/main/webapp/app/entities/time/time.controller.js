(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('TimeController', TimeController);

    TimeController.$inject = ['Time', 'TimeSearch'];

    function TimeController(Time, TimeSearch) {

        var vm = this;

        vm.times = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Time.query(function(result) {
                vm.times = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            TimeSearch.query({query: vm.searchQuery}, function(result) {
                vm.times = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();

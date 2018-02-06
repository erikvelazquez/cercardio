(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('TimesController', TimesController);

    TimesController.$inject = ['Times', 'TimesSearch'];

    function TimesController(Times, TimesSearch) {

        var vm = this;

        vm.times = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Times.query(function(result) {
                vm.times = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            TimesSearch.query({query: vm.searchQuery}, function(result) {
                vm.times = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();

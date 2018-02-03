(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('LivingPlaceController', LivingPlaceController);

    LivingPlaceController.$inject = ['LivingPlace', 'LivingPlaceSearch'];

    function LivingPlaceController(LivingPlace, LivingPlaceSearch) {

        var vm = this;

        vm.livingPlaces = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            LivingPlace.query(function(result) {
                vm.livingPlaces = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            LivingPlaceSearch.query({query: vm.searchQuery}, function(result) {
                vm.livingPlaces = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();

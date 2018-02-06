(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('ReservationCalendarController', ReservationCalendarController);

        ReservationCalendarController.$inject = ['Reservation', 'ReservationSearch'];

    function ReservationCalendarController(Reservation, ReservationSearch) {

        var vm = this;

        vm.reservations = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Reservation.query(function(result) {
                vm.reservations = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ReservationSearch.query({query: vm.searchQuery}, function(result) {
                vm.reservations = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();

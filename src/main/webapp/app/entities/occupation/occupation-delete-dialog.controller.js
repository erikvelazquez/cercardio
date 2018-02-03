(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('OccupationDeleteController',OccupationDeleteController);

    OccupationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Occupation'];

    function OccupationDeleteController($uibModalInstance, entity, Occupation) {
        var vm = this;

        vm.occupation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Occupation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

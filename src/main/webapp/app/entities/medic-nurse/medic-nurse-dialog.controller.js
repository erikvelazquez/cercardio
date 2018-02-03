(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('MedicNurseDialogController', MedicNurseDialogController);

    MedicNurseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'MedicNurse', 'Medic', 'Nurse'];

    function MedicNurseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, MedicNurse, Medic, Nurse) {
        var vm = this;

        vm.medicNurse = entity;
        vm.clear = clear;
        vm.save = save;
        vm.medics = Medic.query({filter: 'medicnurse-is-null'});
        $q.all([vm.medicNurse.$promise, vm.medics.$promise]).then(function() {
            if (!vm.medicNurse.medic || !vm.medicNurse.medic.id) {
                return $q.reject();
            }
            return Medic.get({id : vm.medicNurse.medic.id}).$promise;
        }).then(function(medic) {
            vm.medics.push(medic);
        });
        vm.nurses = Nurse.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.medicNurse.id !== null) {
                MedicNurse.update(vm.medicNurse, onSaveSuccess, onSaveError);
            } else {
                MedicNurse.save(vm.medicNurse, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:medicNurseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
